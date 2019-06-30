package com.mz.fuel_sale_analytics_back.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
public class SignInRequest {

    @Email
    private String email;

    @NotEmpty
    private String password;

}
