package com.aidan.userservice.user.controller.auth;

import com.aidan.security.jwt.JwtService;
import com.aidan.userservice.user.domain.dto.RegisterResponseDTO;
import com.aidan.userservice.user.domain.dto.UserDTO;
import com.aidan.userservice.user.repository.UserRepository;
import com.aidan.userservice.user.repository.mapper.UserMapper;
import com.aidan.userservice.user.service.AuthService;
import com.aidan.userservice.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@AllArgsConstructor
public class AuthController implements AuthControllerApi {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;



    @Override
    public UserDTO register(UserDTO userDTO) {
        return authService.register(userDTO);
    }

    public UserDTO getByEmail(@RequestParam("email") String email) {
        return userMapper.toDto(userService.getByEmail(email));
    }

    @Override
    public RegisterResponseDTO login(LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );


        UserDetails user = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateAccessToken(user.getUsername());

        return new RegisterResponseDTO(token);
        }
        catch (Exception e) {
            log.error("Error while authenticating user", e);
            return null;
        }
    }

    @Override
    public void setPassword(SetPasswordRequest request) {
        authService.setPassword(request.email(), request.newPassword());
    }

    @Override
    public UserDTO confirmAccount(ConfirmRegisterRequest confirmRegisterRequest) {
         return authService.confirmToken(confirmRegisterRequest.token());
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        authService.requestPasswordReset(request.email());
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        authService.resetPassword(request.token(), request.newPassword());
    }

}
