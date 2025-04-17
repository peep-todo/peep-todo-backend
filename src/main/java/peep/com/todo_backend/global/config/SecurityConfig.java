package peep.com.todo_backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import peep.com.todo_backend.global.filter.JwtAuthenticationFilter;
import peep.com.todo_backend.user.repository.UserJpaRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserJpaRepository userJpaRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/user", // 회원가입은 인증 불필요 (POST /api/v1/user)
                                "/api/v1/auth/login", // 로그인도 인증 불필요
                                "/api/v1/auth/refresh",
                                "/swagger-ui/**",
                                "/v3/api-docs/**")
                        .permitAll() // 인증 필요 없는 경로 설정
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .addFilterBefore(new JwtAuthenticationFilter(userJpaRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // PasswordEncoder Bean 등록 (BCrypt 알고리즘 사용)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
