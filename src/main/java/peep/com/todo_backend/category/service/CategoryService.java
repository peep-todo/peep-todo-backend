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
import peep.com.todo_backend.category.dto.CategoryResponseDto;
import peep.com.todo_backend.category.repository.CategoryJpaRepository;
import peep.com.todo_backend.global.Exception.BadRequestException;
import peep.com.todo_backend.global.dto.ResultDto;
import peep.com.todo_backend.global.enums.Type;
import peep.com.todo_backend.global.utils.JwtUtil;
import peep.com.todo_backend.user.domain.User;
import peep.com.todo_backend.user.repository.UserJpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryJpaRepository categoryJpaRepository;
    private final AuthService authService;
    private final UserJpaRepository userJpaRepository;

    // ** 새 카테고리 생성(개인)
    public CategoryResponseDto savePersonalCategory(CategoryDto categoryDto, Integer userId) {

        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 유저입니다."));

        Category newCategory = Category.builder()
                .user(user)
                .team(null)
                .name(categoryDto.getName())
                .isPinned(false)
                .type(Type.PERSONAL)
                .color(categoryDto.getColor())
                .isDeleted(0)
                .build();

        categoryJpaRepository.save(newCategory);

        CategoryResponseDto responseDto = new CategoryResponseDto(newCategory);

        return responseDto;
    }

    // ** 새 카테고리 생성(팀)
    // 팀 정보는 어떻게 받아오는지 모르겠어요..죄송합니다
    public CategoryResponseDto saveTeamCategory(CategoryDto categoryDto, HttpServletRequest request) {

        Category newCategory = Category.builder()
                .user(null)
                .team(null)
                .name(categoryDto.getName())
                .type(Type.TEAM)
                .color(categoryDto.getColor())
                .isDeleted(0)
                .build();

        categoryJpaRepository.save(newCategory);

        CategoryResponseDto responseDto = new CategoryResponseDto(newCategory);

        return responseDto;
    }




    // ** 카테고리 조회
    public CategoryResponseDto getCategory(Integer categoryId) {
        Category category = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 카테고리 입니다."));
        CategoryResponseDto responseDto = new CategoryResponseDto(category);
        return responseDto;
    }

    // ** 카테고리 수정
    public CategoryResponseDto updateCategory(CategoryDto categoryDto,Integer categoryId) {
        Category category = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 카테고리 입니다."));

        category.setName(categoryDto.getName());
        category.setColor(categoryDto.getColor());

        categoryJpaRepository.save(category);

        CategoryResponseDto responseDto = new CategoryResponseDto(category);
        return responseDto;
    }

    // ** 카테고리 삭제
    public CategoryResponseDto deleteCategory(Integer categoryId) {
        Category category = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 카테고리 입니다."));

        category.setIsDeleted(1);

        categoryJpaRepository.save(category);

        CategoryResponseDto responseDto = new CategoryResponseDto(category);
        return responseDto;


    }

    // ** 상단고정
    public CategoryResponseDto pinnedCategory(Integer userId, Integer categoryId) {
        Category category = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 카테고리 입니다."));

        if(category.getUser().getUserId() != userId){
            throw new BadRequestException("자신의 카테고리가 아닙니다.");
        }
        else{
            categoryJpaRepository.unpinAllByUserId(userId);
            category.setPinned(true);
            categoryJpaRepository.save(category);
            return new CategoryResponseDto(category);
        }


    }

    // ** 고정 해제
    public CategoryResponseDto unpinCategory(Integer userId, Integer categoryId) {
        Category category = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 카테고리 입니다."));

        if(category.getUser().getUserId() != userId){
            throw new BadRequestException("자신의 카테고리가 아닙니다.");
        }
        else if(!category.isPinned()){
            throw new BadRequestException("고정되어있는 카테고리가 아닙니다.");
        }
        else{
            category.setPinned(false);
            categoryJpaRepository.save(category);
            return new CategoryResponseDto(category);
        }
    }
}
