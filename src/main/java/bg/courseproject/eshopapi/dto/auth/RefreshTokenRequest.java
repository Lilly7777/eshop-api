package bg.courseproject.eshopapi.dto.auth;

//Snake case to match Firebase Response
public record RefreshTokenRequest(String grant_type, String refresh_token) {}
