package peep.com.todo_backend.category.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import peep.com.todo_backend.category.domain.QCategory;

@RequiredArgsConstructor
public class CategoryJpaCustomRepositoryImpl implements CategoryJpaCustomRepository {

    private final JPAQueryFactory queryFactory;

    // ** 같은 userId를 가진 행의 isPinned를 전부 false로 바꿔줌
    @Transactional
    @Override
    public void unpinAllByUserId(Integer userId) {
        QCategory category = QCategory.category;

        queryFactory
                .update(category)
                .set(category.isPinned, false)
                .where(category.user.userId.eq(userId))
                .execute();
    }
}

