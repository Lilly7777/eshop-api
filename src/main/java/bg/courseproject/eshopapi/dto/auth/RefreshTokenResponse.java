package bg.courseproject.eshopapi.dto.auth;

//Snake case to match Firebase Response
public record RefreshTokenResponse(String id_token) {}
