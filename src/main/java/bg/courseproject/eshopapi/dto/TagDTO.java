package bg.courseproject.eshopapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;


@Data
@JsonRootName(value = "tag")
public class TagDTO {

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "description")
    private String description;

}
