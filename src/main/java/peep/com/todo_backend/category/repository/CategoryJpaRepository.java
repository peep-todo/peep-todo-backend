package peep.com.todo_backend.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peep.com.todo_backend.category.domain.Category;

import java.util.Optional;

@Repository
public interface CategoryJpaRepository extends JpaRepository<Category, Integer> {

    //이름으로 조회
    Optional<Category> findByName(String name);

}
