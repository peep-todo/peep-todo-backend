package peep.com.todo_backend.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peep.com.todo_backend.category.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByCategoryName(String categoryName);
}
