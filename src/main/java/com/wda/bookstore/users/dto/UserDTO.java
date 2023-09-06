package com.wda.bookstore.users.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @ApiModelProperty(required = true)
    private String name;

    @ApiModelProperty(required = true)
    private String address;

    @ApiModelProperty(required = true)
    private String city;

    @ApiModelProperty(example = "user@email.com", required = true)
    @NotEmpty
    @Email
    private String email;
}
