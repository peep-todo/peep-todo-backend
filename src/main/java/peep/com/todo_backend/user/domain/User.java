package peep.com.todo_backend.user.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import com.fasterxml.jackson.annotation.JsonIgnore;

import peep.com.todo_backend.category.domain.Category;
import peep.com.todo_backend.global.domain.BaseTimeEntity;
import peep.com.todo_backend.global.enums.UserRole;
import peep.com.todo_backend.team.domain.TeamUser;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_USER")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Comment("이메일")
    @Column(length = 120, unique = true, nullable = false, name = "email")
    private String email;

    @Comment("비밀번호")
    @Column(length = 120, nullable = true, name = "password")
    private String password;

    @Comment("소셜 토큰")
    @Column(length = 150, nullable = true, name = "token")
    private String token;

    @Comment("유저 타입")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "user_type")
    private UserRole userType;

    @Comment("닉네임")
    @Column(length = 50, nullable = false, name = "nickname")
    private String nickname;

    @Comment("프로필 이미지")
    @Column(length = 50, nullable = false, name = "profile")
    private String profile;

    @Comment("마지막 로그인 시간")
    @Column(nullable = false, name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Comment("삭제 여부")
    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamUser> teamUsers;

    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friend> sentFriendRequests;

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friend> receivedFriendRequests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> userCategory;
}
