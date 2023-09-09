package com.wda.bookstore.api.dto.user;

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

    @NotEmpty
    @ApiModelProperty(required = true)
    private String name;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String address;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String city;

    @ApiModelProperty(example = "user@user.com", required = true)
    @NotEmpty
    @Email
    private String email;
}
