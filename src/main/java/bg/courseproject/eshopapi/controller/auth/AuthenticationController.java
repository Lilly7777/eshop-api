package bg.courseproject.eshopapi.controller.auth;

import bg.courseproject.eshopapi.dto.AuthResponse;
import bg.courseproject.eshopapi.dto.UserDTO;
import bg.courseproject.eshopapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthenticationController {

    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account and returns an authentication token")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDTO userDTO) {
        try {
            AuthResponse authResponse = authService.registerUser(userDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @PostMapping("/auth/login")
    @Operation(summary = "Login a user", description = "Authenticates a user and returns an authentication token")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDTO userDTO) {
        try {
            AuthResponse authResponse = authService.loginUser(userDTO);

            return ResponseEntity.status(HttpStatus.OK).body(authResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @PostMapping("/auth/refreshtoken")
    @Operation(summary = "Refresh authentication token", description = "Refreshes the authentication token using a refresh token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody AuthResponse authResponse) {
        try {
            AuthResponse newAuthResponse = authService.refreshToken(authResponse);

            return ResponseEntity.status(HttpStatus.OK).body(newAuthResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

}
