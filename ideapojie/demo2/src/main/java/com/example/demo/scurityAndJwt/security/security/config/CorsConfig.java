package com.example.demo.scurityAndJwt.security.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration

//public class WebMvcConfig implements WebMvcConfigurer

public class CorsConfig {

    @Value("#{'${cors.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;
    @Value("#{'${cors.allowed-methods}'.split(',')}")
    private List<String> allowedMethods;
    @Value("#{'${cors.allowed-headers}'.split(',')}")
    private List<String> allowedHeaders;
    @Value("#{'${cors.exposed-headers}'.split(',')}")
    private List<String> expectedHeaders;
    //原版没有jwt能用的

    /**
     * CORS 修改后
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);

        configuration.setAllowedMethods(allowedMethods);
//        configuration.addAllowedHeader(HttpHeaders.AUTHORIZATION);
        //允许设置头部
        configuration.setAllowedHeaders(allowedHeaders);
        //允许暴露头部
        configuration.setExposedHeaders(expectedHeaders);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
