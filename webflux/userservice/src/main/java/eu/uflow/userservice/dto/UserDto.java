package eu.uflow.userservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {
    private String id;
    private String name;
    private Double balance;
}
