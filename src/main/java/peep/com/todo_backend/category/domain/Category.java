package peep.com.todo_backend.category.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import peep.com.todo_backend.global.domain.BaseTimeEntity;
import peep.com.todo_backend.global.enums.CategoryType;
import peep.com.todo_backend.team.domain.Team;
import peep.com.todo_backend.user.domain.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "TB_CATEGORY")
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Comment("카테고리명")
    @Column(length = 50, nullable = false, name = "name")
    private String name;

    @Comment("카테고리 타입")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "category_type")
    private CategoryType categoryType;

    @Comment("카테고리 색상")
    @Column(length = 7, nullable = false, name = "color")
    private String color;

    @Comment("삭제 여부")
    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
