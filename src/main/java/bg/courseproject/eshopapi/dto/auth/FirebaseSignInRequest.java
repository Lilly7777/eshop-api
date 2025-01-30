package bg.courseproject.eshopapi.dto.auth;

public record FirebaseSignInRequest(String email, String password, boolean returnSecureToken) {}
