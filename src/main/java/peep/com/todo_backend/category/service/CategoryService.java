package peep.com.todo_backend.category.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import peep.com.todo_backend.auth.service.AuthService;
import peep.com.todo_backend.category.domain.Category;
import peep.com.todo_backend.category.dto.CategoryDto;
import peep.com.todo_backend.category.repository.CategoryJpaRepository;
import peep.com.todo_backend.global.Exception.BadRequestException;
import peep.com.todo_backend.global.dto.ResultDto;
import peep.com.todo_backend.global.enums.Type;
import peep.com.todo_backend.global.utils.JwtUtil;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryJpaRepository categoryJpaRepository;
    private final AuthService authService;

    // ** 현재 로그인한 유저 id 가져오기
    public Integer getCurrentUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        return JwtUtil.getUserId(token);
    }

    // ** 새 카테고리 생성
    public ResponseEntity<?> saveCategory(CategoryDto categoryDto, HttpServletRequest request) {
        int userId = getCurrentUserId(request);

        if(categoryDto.getType() == Type.PERSONAL) {
            Category newCategory = Category.builder()
                    .userId(userId)
                    .teamId(null)
                    .name(categoryDto.getName())
                    .type(Type.PERSONAL)
                    .color(categoryDto.getColor())
                    .isDeleted(0)
                    .build();

            categoryJpaRepository.save(newCategory);

            return ResponseEntity.ok(ResultDto.res(HttpStatus.OK,"SUCCESS","개인 카테고리 생성 성공"));
        }

        // !! 카테고리가 TEAM일때 실행되는 코드입니다! 이부분 수정해주시면 될것같아요
        else if(categoryDto.getType() == Type.TEAM) {
            Category newCategory = Category.builder()
                    .userId(userId)
                    .teamId(null)
                    .name(categoryDto.getName())
                    .type(Type.PERSONAL)
                    .color(categoryDto.getColor())
                    .isDeleted(0)
                    .build();

            categoryJpaRepository.save(newCategory);

            return ResponseEntity.ok(ResultDto.res(HttpStatus.OK,"SUCCESS","팀 카테고리 생성 성공"));
            // !! 요기까지에요!
        }
        else{
            throw  new BadRequestException("카테고리 타입을 확인해주세요");
        }
    }

    // ** 카테고리 조회
    public ResponseEntity<?> getCategory(Integer categoryId) {
        Category category = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 카테고리 입니다."));
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK,"SUCCESS",category));
    }

    // ** 카테고리 수정
    public ResponseEntity<?> updateCategory(CategoryDto categoryDto,Integer categoryId) {
        Category category = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 카테고리 입니다."));

        category.setName(categoryDto.getName());
        category.setColor(categoryDto.getColor());
        category.setType(categoryDto.getType());

        categoryJpaRepository.save(category);

        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK,"SUCCESS","카테고리 정보 수정 성공"));
    }

    // ** 카테고리 삭제
    public ResponseEntity<?> deleteCategory(Integer categoryId) {
        Category category = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 카테고리 입니다."));

        category.setIsDeleted(1);

        categoryJpaRepository.save(category);

        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK,"SUCCESS","카테고리 삭제 성공"));


    }
}
