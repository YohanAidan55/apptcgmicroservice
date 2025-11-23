package com.aidan.userservice.user.domain.contract;

import com.aidan.userservice.user.domain.enums.AuthenticationProvider;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public interface UserContract {

    @NotNull
    @Length(min = 1, max = 20)
    String getFirstName();

    @NotNull
    @Length(min = 1, max = 20)
    String getLastName();

    @NotNull
    @Length(min = 1, max = 20)
    String getUserName();

    @NotNull
    @Email
    String getEmail();

    // Password can be null for OAuth providers
    @Length(min = 6, max = 50)
    String getPassword();

    AuthenticationProvider getProvider();
}
