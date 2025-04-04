package peep.com.todo_backend.category.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import peep.com.todo_backend.global.domain.BaseTimeEntity;
import peep.com.todo_backend.global.enums.TeamUserRole;
import peep.com.todo_backend.global.enums.Type;
import peep.com.todo_backend.team.domain.Team;
import peep.com.todo_backend.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_CATEGORY")

public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Comment("색상")
    @Column(length = 10, nullable = false, name = "color")
    private String color;

    @Comment("상단 고정 여부")
    @Column(nullable = false, name = "is_pinned")
    private boolean isPinned;

    @Comment("카테고리 이름")
    @Column(length = 50, nullable = false, name = "name")
    private String name;

    @Comment("카테고리 타입")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type")
    private Type type;

    @Comment("삭제여부")
    @Column(nullable = false, name = "is_deleted")
    private Integer isDeleted;

}
