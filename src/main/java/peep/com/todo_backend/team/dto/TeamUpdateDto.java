package peep.com.todo_backend.team.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TeamUpdateDto extends TeamDto{
    public TeamUpdateDto(String name, String projectName, String description, LocalDate startDate, LocalDate endDate) {
        super(name, projectName, description, startDate, endDate);
    }
}
