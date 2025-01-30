package bg.courseproject.eshopapi.service;

import bg.courseproject.eshopapi.dto.AuthResponse;
import bg.courseproject.eshopapi.dto.UserDTO;
import bg.courseproject.eshopapi.dto.auth.FirebaseSignInRequest;
import bg.courseproject.eshopapi.dto.auth.FirebaseSignInResponse;
import bg.courseproject.eshopapi.dto.auth.RefreshTokenRequest;
import bg.courseproject.eshopapi.dto.auth.RefreshTokenResponse;
import bg.courseproject.eshopapi.entity.User;
import bg.courseproject.eshopapi.exception.AuthenticationException;
import bg.courseproject.eshopapi.mapper.UserMapper;
import bg.courseproject.eshopapi.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class AuthService {

    private final FirebaseAuth firebaseAuth;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Value("${eshop.web-api-key}")
    private String webApiKey;

    @Value("${eshop.firebase.auth.url}")
    private String firebaseAuthUrl;

    @Value("${eshop.firebase.auth.refresh.url}")
    private String firebaseRefreshAuthUrl;

    private static final String INVALID_CREDENTIALS_ERROR = "INVALID_LOGIN_CREDENTIALS";
    private static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
    private static final String INVALID_REFRESH_TOKEN_ERROR = "INVALID_REFRESH_TOKEN";

    @Autowired
    public AuthService(FirebaseAuth firebaseAuth, UserMapper userMapper, UserRepository userRepository) {
        this.firebaseAuth = firebaseAuth;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public AuthResponse registerUser(UserDTO userDTO) throws Exception {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(userDTO.getEmail())
                .setPassword(userDTO.getPassword())
                .setDisplayName(userDTO.getFirstName() + " " + userDTO.getLastName());

        UserRecord userRecord = firebaseAuth.createUser(request);

        User userEntity = userMapper.fromDTO(userDTO);
        userEntity.setFid(userRecord.getUid());
        userRepository.save(userEntity);

        String firebaseToken = firebaseAuth.createCustomToken(userRecord.getUid());

        return new AuthResponse(firebaseToken);
    }

    public AuthResponse loginUser(UserDTO userDTO) {
        FirebaseSignInResponse firebaseSignInResponse = login(userDTO.getEmail(), userDTO.getPassword());
        userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new AuthenticationException("User not found"));

        return new AuthResponse(firebaseSignInResponse.idToken());
    }


    public AuthResponse refreshToken(AuthResponse authResponse) {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(REFRESH_TOKEN_GRANT_TYPE, authResponse.getToken());
        RefreshTokenResponse refreshTokenResponse = sendRefreshTokenRequest(refreshTokenRequest);

        return new AuthResponse(refreshTokenResponse.id_token());
    }

    private FirebaseSignInResponse login(String emailId, String password) {
        FirebaseSignInRequest requestBody = new FirebaseSignInRequest(emailId, password, true);
        return sendSignInRequest(requestBody);
    }

    private FirebaseSignInResponse sendSignInRequest(FirebaseSignInRequest firebaseSignInRequest) {
        try {
            return RestClient.create(firebaseAuthUrl)
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam(webApiKey, webApiKey)
                            .build())
                    .body(firebaseSignInRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(FirebaseSignInResponse.class);
        } catch (HttpClientErrorException exception) {
            if (exception.getResponseBodyAsString().contains(INVALID_CREDENTIALS_ERROR)) {
                throw new AuthenticationException("Invalid login credentials provided");
            }
            throw exception;
        }
    }

    private RefreshTokenResponse sendRefreshTokenRequest(RefreshTokenRequest refreshTokenRequest) {
        try {
            return RestClient.create(firebaseRefreshAuthUrl)
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam(webApiKey, webApiKey)
                            .build())
                    .body(refreshTokenRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(RefreshTokenResponse.class);
        } catch (HttpClientErrorException exception) {
            if (exception.getResponseBodyAsString().contains(INVALID_REFRESH_TOKEN_ERROR)) {
                throw new AuthenticationException("Invalid refresh token provided");
            }
            throw exception;
        }
    }

}
