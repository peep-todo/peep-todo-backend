package peep.com.todo_backend.category.repository;


public interface CategoryJpaCustomRepository {
    // ** isPinned 전체 해제
    void unpinAllByUserId(Integer userId);
}
