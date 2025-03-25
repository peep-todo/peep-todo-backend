package peep.com.todo_backend.category.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import peep.com.todo_backend.global.domain.BaseTimeEntity;
import peep.com.todo_backend.global.enums.TeamUserRole;
import peep.com.todo_backend.global.enums.Type;
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

    @Comment("유저ID")
    @Column(name = "user_id")
    private Integer userId;

    @Comment("팀ID")
    @Column(name = "teamId")
    private Integer teamId;

    @Comment("카테고리 이름")
    @Column(length = 50, nullable = false, name = "name")
    private String name;

    @Comment("카테고리 타입")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type")
    private Type type;

    @Comment("색상")
    @Column(length = 10, nullable = false, name = "color")
    private String color;

//    @Comment("생성시간")
//    @CreatedDate
//    @Column(nullable = false, name = "created_date")
//    private LocalDateTime createdDate;
//
//    @Comment("수정시간")
//    @LastModifiedDate
//    @Column(nullable = false, name = "modified_date")
//    private LocalDateTime modifiedDate;

    @Comment("삭제여부")
    @Column(nullable = false, name = "is_deleted")
    private Integer isDeleted;

}
