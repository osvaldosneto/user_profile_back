package com.user.userprofile.DTO;

import lombok.Data;

@Data
public class UserLoginDTO {

    private String username;
    private String password;
    private String profileType;
    private String token;

}
