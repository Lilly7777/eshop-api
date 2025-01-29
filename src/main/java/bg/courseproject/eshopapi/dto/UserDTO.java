package bg.courseproject.eshopapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;


@Data
@JsonRootName(value = "user")
public class UserDTO {

    @JsonProperty(value = "email", required = true)
    private String email;

    @JsonProperty(value = "first_name", required = true)
    private String firstName;

    @JsonProperty(value = "last_name", required = true)
    private String lastName;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "password", required = true)
    private String password;

}
