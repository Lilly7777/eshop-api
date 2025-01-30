package bg.courseproject.eshopapi.filter;

import bg.courseproject.eshopapi.exception.AuthenticationException;
import bg.courseproject.eshopapi.provider.FirebaseAuthenticationProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class FirebaseAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final FirebaseAuthenticationProvider firebaseAuthenticationProvider;

    public FirebaseAuthenticationFilter(FirebaseAuthenticationProvider firebaseAuthenticationProvider) {
        super(new AntPathRequestMatcher("/v1/api/**"));
        this.firebaseAuthenticationProvider = firebaseAuthenticationProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(token, token);
            return firebaseAuthenticationProvider.authenticate(authRequest);
        } else {
            throw new AuthenticationException("No token provided");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
}


