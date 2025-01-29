package bg.courseproject.eshopapi.dto;

import bg.courseproject.eshopapi.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;


@Data
@JsonRootName(value = "category")
public class CategoryDTO {

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "parent_category")
    private Category parentCategory;

}
