package com.example.ntseu_test_task_manager.controller;

import com.example.ntseu_test_task_manager.dto.auth.LoginRequestDto;
import com.example.ntseu_test_task_manager.dto.UserRequestDto;
import com.example.ntseu_test_task_manager.dto.auth.LoginResponseDto;
import com.example.ntseu_test_task_manager.security.service.AuthService;
import com.example.ntseu_test_task_manager.service.UserService;
import com.example.ntseu_test_task_manager.util.ApiUrls;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthController {
    private UserService userService;
    private AuthService authService;

   @Operation(summary = "Register new user")
   @ApiResponses({
           @ApiResponse(responseCode = "200", description = "User created"),
           @ApiResponse(responseCode = "400", description = "Validation error"),
           @ApiResponse(responseCode = "409", description = "Email already exists")
   })
   @PostMapping(ApiUrls.REGISTER)
   public void register(@RequestBody @Valid UserRequestDto user) {
       log.info("Register request for email={}", user.email());
       userService.save(user);
       log.info("User {} registered successfully", user.email());
   }


    @Operation(summary = "Login user and get JWT token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "JWT token returned"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
   @PostMapping(ApiUrls.LOGIN)
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
       log.info("Login attempt for email={}", loginRequestDto.username());
        LoginResponseDto token = authService.login(loginRequestDto);
       log.info("User {} logged in successfully", loginRequestDto.username());
        return ResponseEntity.ok(token);
   }

}
