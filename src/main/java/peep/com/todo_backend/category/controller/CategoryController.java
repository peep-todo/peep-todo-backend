package peep.com.todo_backend.category.controller;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import peep.com.todo_backend.category.dto.CategoryResponseDto;
import peep.com.todo_backend.category.dto.CategorySaveDto;
import peep.com.todo_backend.category.dto.CategoryUpdateDto;
import peep.com.todo_backend.category.service.CategoryService;
import peep.com.todo_backend.global.Response.ApiSuccessResponse;
import peep.com.todo_backend.global.customAnnotation.swagger.SwaggerApiNotFoundError;
import peep.com.todo_backend.global.customAnnotation.swagger.SwaggerApiSuccess;
import peep.com.todo_backend.global.customAnnotation.swagger.SwaggerInternetServerError;
import peep.com.todo_backend.global.dto.ResultDto;

@RestController
@RequestMapping("/api/v1/category")
@Tag(name = "Category API", description = "카테고리 관련 API")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // Personal 카테고리 생성
    @SwaggerApiSuccess(summary = "유저 카테고리 생성", description = "신규 카테고리를 생성합니다.", value = ApiSuccessResponse.CATEGORY_SAVE)
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @PostMapping
    public ResponseEntity<?> savePersonalCategory(
        @Valid @RequestBody CategorySaveDto dto,
        @AuthenticationPrincipal Integer userId
    ) {
        categoryService.saveCategory(dto, userId, null);
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, "SUCCESS", "카테고리 생성 성공", null));
    }

    // Team 카테고리 생성
    @SwaggerApiSuccess(summary = "팀 카테고리 생성", description = "신규 카테고리를 생성합니다.", value = ApiSuccessResponse.CATEGORY_SAVE)
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @PostMapping("/team")
    public ResponseEntity<?> saveTeamCategory(
        @Valid @RequestBody CategorySaveDto dto,
        @AuthenticationPrincipal Integer userId,
        @RequestParam Integer teamId
    ) {
        categoryService.saveCategory(dto, userId, teamId);
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, "SUCCESS", "카테고리 생성 성공", null));
    }

    // Personal 카테고리 목록 조회
    @SwaggerApiSuccess(
        summary = "Personal 카테고리 조회",
        description = "로그인 유저의 Personal 카테고리 목록을 조회합니다.",
        value = ApiSuccessResponse.CATEGORY_GET
    )
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @GetMapping("/category")
    public ResponseEntity<?> getPersonalCategories(
        @AuthenticationPrincipal Integer userId
    ) {
        List<CategoryResponseDto> list =
            categoryService.getCategories(userId, null);
        return ResponseEntity.ok(
            ResultDto.res(HttpStatus.OK, "SUCCESS", "카테고리 목록 조회 성공", list)
        );
    }

    // Team 카테고리 목록 조회
    @SwaggerApiSuccess(
        summary = "Team 카테고리 조회",
        description = "주어진 teamId의 Team 카테고리 목록을 조회합니다.",
        value = ApiSuccessResponse.CATEGORY_GET
    )
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @GetMapping("/team/category")
    public ResponseEntity<?> getTeamCategories(
        @RequestParam("teamId") Integer teamId,
        @AuthenticationPrincipal Integer userId
    ) {
        List<CategoryResponseDto> list =
            categoryService.getCategories(userId, teamId);
        return ResponseEntity.ok(
            ResultDto.res(HttpStatus.OK, "SUCCESS", "팀 카테고리 목록 조회 성공", list)
        );
    }

    // Personal 카테고리 수정
    @SwaggerApiSuccess(summary = "카테고리 수정", description = "카테고리를 수정합니다.", value = ApiSuccessResponse.CATEGORY_UPDATE)
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @PutMapping("/updateCategory")
    public ResponseEntity<?> updatePersonalCategory(
        @RequestParam Integer categoryId,
        @Validated @RequestBody CategoryUpdateDto dto,
        @AuthenticationPrincipal Integer userId
    ) {
        categoryService.updateCategory(dto, userId, null, categoryId);
        return ResponseEntity.ok(
            ResultDto.res(HttpStatus.OK, "SUCCESS", "카테고리 수정 성공", null)
        );
    }

    // Team 카테고리 수정
    @SwaggerApiSuccess(summary = "팀 카테고리 수정", description = "카테고리를 수정합니다.", value = ApiSuccessResponse.CATEGORY_UPDATE)
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @PutMapping("/team/updateCategory")
    public ResponseEntity<?> updateTeamCategory(
        @RequestParam Integer teamId,
        @RequestParam Integer categoryId,
        @Validated @RequestBody CategoryUpdateDto dto,
        @AuthenticationPrincipal Integer userId
    ) {
        categoryService.updateCategory(dto, userId, teamId, categoryId);
        return ResponseEntity.ok(
            ResultDto.res(HttpStatus.OK, "SUCCESS", "팀 카테고리 수정 성공", null)
        );
    }

    // Personal 카테고리 삭제
    @SwaggerApiSuccess(
        summary = "카테고리 삭제",
        description = "Personal 카테고리를 삭제합니다.",
        value = ApiSuccessResponse.CATEGORY_DELETE
    )
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @DeleteMapping("/deleteCategory")
    public ResponseEntity<?> deletePersonalCategory(
        @RequestParam("categoryId") Integer categoryId,
        @AuthenticationPrincipal Integer userId
    ) {
        categoryService.deleteCategory(userId, null, categoryId);
        return ResponseEntity.ok(
            ResultDto.res(HttpStatus.OK, "SUCCESS", "카테고리 삭제 성공", null)
        );
    }

    // Team 카테고리 삭제
    @SwaggerApiSuccess(
        summary = "카테고리 삭제",
        description = "Team 카테고리를 삭제합니다.",
        value = ApiSuccessResponse.CATEGORY_DELETE
    )
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @DeleteMapping("/team/deleteCategory")
    public ResponseEntity<?> deleteTeamCategory(
        @RequestParam("teamId") Integer teamId,
        @RequestParam("categoryId") Integer categoryId,
        @AuthenticationPrincipal Integer userId
    ) {
        categoryService.deleteCategory(userId, teamId, categoryId);
        return ResponseEntity.ok(
            ResultDto.res(HttpStatus.OK, "SUCCESS", "팀 카테고리 삭제 성공", null)
        );
    }
}
