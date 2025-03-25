package peep.com.todo_backend.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import peep.com.todo_backend.category.domain.Category;
import peep.com.todo_backend.global.enums.Type;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    @Schema(description = "카테고리 이름", example = "PEEP")
    @NotNull(message = "카테고리 이름은 필수값입니다.")
    private String name;

    @Schema(description = "색상", example = "Blue")
    @NotNull(message = "색상은 필수값입니다.")
    private String color;

    @Schema(description = "카테고리 타입", example = "PERSONAL")
    @NotNull(message = "카테고리 타입은 필수값입니다.")
    private Type type;


}
