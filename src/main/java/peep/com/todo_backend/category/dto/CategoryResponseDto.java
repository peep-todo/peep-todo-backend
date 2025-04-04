package peep.com.todo_backend.category.dto;

import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peep.com.todo_backend.category.domain.Category;
import peep.com.todo_backend.global.enums.Type;
import peep.com.todo_backend.team.domain.Team;
import peep.com.todo_backend.user.domain.User;

@Getter
@Setter
@NoArgsConstructor

public class CategoryResponseDto {

    private Integer categoryId;
    private Integer userId;
    private Integer teamId;
    private String color;
    private String name;
    private boolean isPinned;
    private Type type;

    public CategoryResponseDto(Category category){
        this.categoryId = category.getCategoryId();
        this.userId = category.getUser().getUserId();
        this.teamId = category.getTeam() != null ? category.getTeam().getTeamId() : null;
        this.isPinned = category.isPinned();
        this.color = category.getColor();
        this.name = category.getName();
        this.type = category.getType();
    }
}
