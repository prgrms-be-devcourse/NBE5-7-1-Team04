package com.example.gridscircles.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/admin/login").anonymous()
                .requestMatchers(HttpMethod.POST, "/admin/login").permitAll()
                .requestMatchers("/admin/logout").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().permitAll()
            )

            .formLogin(form -> form
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/admin/products/list")
                .failureUrl("/admin/login?error")
            )

            .logout(logout -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            );
        return http.build();
    }
}
