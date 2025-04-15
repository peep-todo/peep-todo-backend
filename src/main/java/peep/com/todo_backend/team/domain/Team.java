package peep.com.todo_backend.team.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import peep.com.todo_backend.category.domain.Category;
import peep.com.todo_backend.global.domain.BaseTimeEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_TEAM")
public class Team extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamId;

    @Comment("팀 이름")
    @Column(length = 50, nullable = false, name = "name")
    private String name;

    @Comment("프로젝트 이름")
    @Column(length = 50, nullable = false, name = "project_name")
    private String projectName;

    @Comment("설명")
    @Column(length = 150, nullable = true, name = "description")
    private String description;

    @Comment("시작 날짜")
    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @Comment("종료 날짜")
    @Column(nullable = false, name = "end_date")
    private LocalDate endDate;

    @Comment("초대 링크")
    @Column(length = 255, name = "invite_link")
    private String inviteLink;

    @Comment("초대 링크 만료 시간")
    @Column(name = "invite_expiry")
    private LocalDateTime inviteExpiry;

    @Comment("삭제 여부")
    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted;

    @Column(unique = true, nullable = false)
    private String teamToken;

    @JsonManagedReference
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamUser> teamUsers;

    @JsonManagedReference
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> teamCategory;
}
