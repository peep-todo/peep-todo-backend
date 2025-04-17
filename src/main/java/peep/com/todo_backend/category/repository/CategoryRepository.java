package peep.com.todo_backend.category.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import peep.com.todo_backend.category.domain.Category;

import java.util.Optional;
import peep.com.todo_backend.global.enums.CategoryType;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Personal 카테고리 조회
    List<Category> findByUser_UserIdAndCategoryType(
        Integer userId, CategoryType categoryType);

    // Team 카테고리 조회
    List<Category> findByTeam_TeamIdAndCategoryType(
        Integer teamId, CategoryType categoryType);

    // Personal 중복 검사
    Optional<Category> findByUser_UserIdAndName(
        Integer userId, String name);

    // Team 중복 검사
    Optional<Category> findByTeam_TeamIdAndName(
        Integer teamId, String name);

    // 고정된(pinned=true) 카테고리 조회
    Optional<Category> findByIsPinnedTrue();
}
