package com.coaching.institute.security;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        // Public APIs
                        .requestMatchers("/api/auth/**").permitAll()

                        // Teacher-only routes
                        .requestMatchers("/api/marks/add").hasAuthority("teacher")
                        .requestMatchers("/api/attendance/mark").hasAuthority("teacher")
                        .requestMatchers("/api/assignments/upload").hasAuthority("teacher")

                        // Student-only
                        .requestMatchers("/api/assignments/**/submit").hasAuthority("student")
                        .requestMatchers("/api/marks/summary/student/**").permitAll()
                        // Admin-only
                        .requestMatchers("/api/admin/**").hasAuthority("admin")
                        .requestMatchers("/api/classes/create").hasAuthority("admin")
                        .requestMatchers("/api/users/**").hasAuthority("admin")

                        // Any other endpoint requires login
                        .anyRequest().authenticated()
                )

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, excep) -> {
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                }))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

}
