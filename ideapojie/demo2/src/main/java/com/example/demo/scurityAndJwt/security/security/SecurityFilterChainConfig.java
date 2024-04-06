package com.example.demo.scurityAndJwt.security.security;

import com.example.demo.scurityAndJwt.security.jwt.JWTAuthenticationFliter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFliter jwtAuthenticationFliter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    public SecurityFilterChainConfig(AuthenticationProvider authenticationProvider,
                                     JWTAuthenticationFliter jwtAuthenticationFliter,
                                     AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFliter = jwtAuthenticationFliter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }
//    安全筛选器
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/v1/customers",
                                        "/api/v1/auth/login"
                                )
                                .permitAll()


                                .requestMatchers(HttpMethod.GET,"/ping")
                                .permitAll()

                                .requestMatchers(HttpMethod.GET,"/actuator/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/v1/medicine")
                                .permitAll()

//
//                                .requestMatchers(HttpMethod.GET,"/api/v1/medicine")
//                                .permitAll()

                                .anyRequest()
                                .authenticated())
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFliter,
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);
        return http.build();
    }


}
