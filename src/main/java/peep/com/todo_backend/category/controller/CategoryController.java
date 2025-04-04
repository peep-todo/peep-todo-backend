package peep.com.todo_backend.category.controller;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import peep.com.todo_backend.category.domain.Category;
import peep.com.todo_backend.category.dto.CategoryDto;
import peep.com.todo_backend.category.dto.CategoryResponseDto;
import peep.com.todo_backend.category.service.CategoryService;
import peep.com.todo_backend.global.customAnnotation.swagger.SwaggerApiNotFoundError;
import peep.com.todo_backend.global.customAnnotation.swagger.SwaggerApiSuccess;
import peep.com.todo_backend.global.customAnnotation.swagger.SwaggerInternetServerError;
import peep.com.todo_backend.global.dto.ResultDto;
import peep.com.todo_backend.user.dto.UserSaveDto;
import peep.com.todo_backend.user.dto.UserUpdateDto;

@RestController
@RequestMapping("/api/v1/category")
@Tag(name = "Category API", description = "카테고리 관련 API")
@Slf4j
@RequiredArgsConstructor

public class CategoryController {

    private final CategoryService categoryService;

    // ** 카테고리 생성(개인)
    @SwaggerApiSuccess(summary = "카테고리 생성", description = "새 카테고리를 생성합니다.")
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @PostMapping
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto dto,  @AuthenticationPrincipal Integer userId) {
        CategoryResponseDto response = categoryService.savePersonalCategory(dto, userId);
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK,"개인 카테고리 생성 성공",response));
    }

    // ** 카테고리(팀)

    // ** 카테고리 조회
    @SwaggerApiSuccess(summary = "카테고리 정보 조회", description = "카테고리 정보를 조회합니다.")
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @GetMapping("/getCategory")
    public ResponseEntity<?> getCategory(
            @RequestParam(name = "categoryId") @Parameter(description = "조회할 카테고리 ID", required = true) Integer categoryId) {
        CategoryResponseDto response = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK,"카테고리 정보를 조회합니다",response));
    }


    // ** 카테고리 정보 업데이트
    @SwaggerApiSuccess(summary = "카테고리 정보 수정", description = "카테고리의 정보를 수정합니다.")
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @PutMapping("/updateCategory")
    public ResponseEntity<?> updateCategory(
            @Valid @RequestBody CategoryDto dto,
            @RequestParam(name = "categoryId") @Parameter(description = "수정할 카테고리 ID", required = true) Integer categoryId) {
            CategoryResponseDto response = categoryService.updateCategory(dto, categoryId);
            return ResponseEntity.ok(ResultDto.res(HttpStatus.OK,"카테고리 수정 완료",response));
    }

    // ** 카테고리 삭제
    @SwaggerApiSuccess(summary = "카테고리 삭제", description = "카테고리를 삭제합니다.")
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @DeleteMapping("/deleteCategory")
    public ResponseEntity<?> deleteCategory(
            @RequestParam(name = "categoryId") @Parameter(description = "삭제할 카테고리 ID", required = true) Integer categoryId) {
            CategoryResponseDto response = categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(ResultDto.res(HttpStatus.OK,"카테고리 삭제 완료",response));
    }

    // ** 카테고리 상단고정
    @SwaggerApiSuccess(summary = "카테고리 상단고정", description = "카테고리 상단 고정을 합니다")
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @PutMapping("/pinnedCategory")
    public ResponseEntity<?> pinnedCategory(
            @RequestParam(name = "categoryId") @Parameter(description = "고정할 카테고리 ID", required = true) Integer categoryId, @AuthenticationPrincipal Integer userId) {
            CategoryResponseDto response = categoryService.pinnedCategory(userId, categoryId);
            return ResponseEntity.ok(ResultDto.res(HttpStatus.OK,"카테고리 고정 완료",response));
    }

    // ** 카테고리 고정 해제
    @SwaggerApiSuccess(summary = "카테고리 고정해제", description = "카테고리 상단 고정을 해제합니다")
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @PutMapping("/unPinnedCategory")
    public ResponseEntity<?> unPinCategory(
            @RequestParam(name = "categoryId") @Parameter(description = "고정 해제할 카테고리 ID", required = true) Integer categoryId, @AuthenticationPrincipal Integer userId) {
        CategoryResponseDto response = categoryService.unpinCategory(userId, categoryId);
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK,"카테고리 고정 해제 완료",response));
    }

}
