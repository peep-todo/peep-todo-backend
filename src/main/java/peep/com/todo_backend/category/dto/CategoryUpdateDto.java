package peep.com.todo_backend.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateDto {
    @Schema(description = "새 카테고리명", example = "새알바")
    private String name;

    @Schema(description = "새 카테고리 색상", example = "#FFEECC")
    private String color;

    @Schema(description = "상단 고정 여부", example = "true")
    private boolean pinned;
}
