package com.koragpt.koragpt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Frontend origin(s)
        config.setAllowedOrigins(List.of(
                "http://localhost:3000"   // Next.js dev
                // add more if needed, e.g. "http://127.0.0.1:3000"
        ));

        // Methods your API uses
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // Headers the browser may send
        config.setAllowedHeaders(List.of("*"));

        // If you use cookies/session or Authorization header in the browser:
        config.setAllowCredentials(true);

        // Optional: expose custom headers to JS (if you set any)
        config.setExposedHeaders(List.of("Location"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
