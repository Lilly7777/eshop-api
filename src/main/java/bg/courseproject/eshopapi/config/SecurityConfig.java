package bg.courseproject.eshopapi.config;


import bg.courseproject.eshopapi.filter.FirebaseAuthenticationFilter;
import bg.courseproject.eshopapi.provider.FirebaseAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final FirebaseAuthenticationProvider firebaseAuthenticationProvider;


    @Autowired
    public SecurityConfig(FirebaseAuthenticationProvider firebaseAuthenticationProvider) {
        this.firebaseAuthenticationProvider = firebaseAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/v1/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(new FirebaseAuthenticationFilter(firebaseAuthenticationProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(firebaseAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }
}