package peep.com.todo_backend.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import peep.com.todo_backend.global.enums.CategoryType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySaveDto {
    @Schema(description = "카테고리 타입", example = "PERSONAL")
    private CategoryType categoryType;

    @Schema(description = "카테고리명", example = "알바")
    private String name;

    @Schema(description = "카테고리 색상", example = "#CBACFF")
    private String color;

    @Schema(description = "카테고리 상단 고정", example = "true")
    private boolean isPinned;
}
