package peep.com.todo_backend.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peep.com.todo_backend.category.domain.Category;
import peep.com.todo_backend.category.dto.CategorySaveDto;
import peep.com.todo_backend.category.repository.CategoryRepository;
import peep.com.todo_backend.global.Exception.BadRequestException;
import peep.com.todo_backend.global.enums.CategoryType;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

   public String saveCategory(CategorySaveDto dto) {
       Optional<Category> existCategory = categoryRepository.findByCategoryName(dto.getName());

       if(existCategory.isPresent()) {
            throw new BadRequestException("이미 존재하는 카테고리명입니다.");
       }

       Category category = Category.builder()
       .categoryType(CategoryType.PERSONAL)
       .categoryName
   }

}
