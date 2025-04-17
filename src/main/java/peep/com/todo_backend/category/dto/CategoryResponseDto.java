package peep.com.todo_backend.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import peep.com.todo_backend.global.enums.CategoryType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    @Schema(description = "카테고리 식별자")
    private Integer categoryId;

    @Schema(description = "카테고리명")
    private String name;

    @Schema(description = "카테고리 색상", example = "#CBACFF")
    private String color;

    @Schema(description = "상단 고정 여부")
    private boolean pinned;

    @Schema(description = "카테고리 타입")
    private CategoryType categoryType;
}
