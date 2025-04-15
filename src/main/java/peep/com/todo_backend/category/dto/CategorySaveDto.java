package peep.com.todo_backend.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySaveDto {
    @Schema(description = "카테고리명", example = "알바")
    private String name;

    @Schema(description = "카테고리 색상", example = "#CBACFF")
    private String color;
}
