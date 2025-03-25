package peep.com.todo_backend.category.controller;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import peep.com.todo_backend.category.dto.CategoryDto;
import peep.com.todo_backend.category.service.CategoryService;
import peep.com.todo_backend.global.customAnnotation.swagger.SwaggerApiNotFoundError;
import peep.com.todo_backend.global.customAnnotation.swagger.SwaggerApiSuccess;
import peep.com.todo_backend.global.customAnnotation.swagger.SwaggerInternetServerError;
import peep.com.todo_backend.user.dto.UserSaveDto;
import peep.com.todo_backend.user.dto.UserUpdateDto;

@RestController
@RequestMapping("/api/v1/category")
@Tag(name = "Category API", description = "카테고리 관련 API")
@Slf4j
@RequiredArgsConstructor

public class CategoryController {

    private final CategoryService categoryService;

    // ** 카테고리 생성
    @SwaggerApiSuccess(summary = "카테고리 생성", description = "새 카테고리를 생성합니다.")
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @PostMapping
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto dto, HttpServletRequest request) {
        return categoryService.saveCategory(dto,request);
    }

    @SwaggerApiSuccess(summary = "카테고리 정보 조회", description = "카테고리 정보를 조회합니다.")
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @GetMapping("/getCategory")
    public ResponseEntity<?> getUser(
            @RequestParam(name = "categoryId") @Parameter(description = "조회할 카테고리 ID", required = true) Integer categoryId) {
        return categoryService.getCategory(categoryId);
    }

    // ** 카테고리 정보 업데이트
    @SwaggerApiSuccess(summary = "카테고리 정보 수정", description = "카테고리의 정보를 수정합니다.")
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @PutMapping("/updateCategory")
    public ResponseEntity<?> updateCategory(
            @Valid @RequestBody CategoryDto dto,
            @RequestParam(name = "categoryId") @Parameter(description = "수정할 카테고리 ID", required = true) Integer categoryId) {
        return categoryService.updateCategory(dto,categoryId);
    }

    // ** 카테고리 삭제
    @SwaggerApiSuccess(summary = "카테고리 삭제", description = "카테고리를 삭제합니다.")
    @SwaggerApiNotFoundError
    @SwaggerInternetServerError
    @DeleteMapping("/deleteCategory")
    public ResponseEntity<?> deleteCategory(
            @RequestParam(name = "categoryId") @Parameter(description = "삭제할 카테고리 ID", required = true) Integer categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

}
