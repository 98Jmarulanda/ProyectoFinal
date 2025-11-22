package com.proyecto.odontologia.backend_odontologia.Security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.proyecto.odontologia.backend_odontologia.Security.filter.JwtAuthenticationFilter;
import com.proyecto.odontologia.backend_odontologia.Security.filter.JwtValidationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Filtro de autenticación (login)
        JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/login");
        
        return http.authorizeHttpRequests( (authz) -> authz
        .requestMatchers(HttpMethod.GET, "/users").hasAuthority("ROLE_ADMIN")
        .requestMatchers(HttpMethod.POST, "/login").permitAll()
        .requestMatchers(HttpMethod.POST, "/users/create").permitAll()
        .anyRequest().authenticated())
        .addFilter(authenticationFilter)
        .addFilter(new JwtValidationFilter(authenticationManager()))
        .csrf(config -> config.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
    }

    // @Bean
    // SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    //     // Filtro real de login
    //     JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(authenticationManager());
    //     authenticationFilter.setFilterProcessesUrl("/login");

    //     return http
    //         .authorizeHttpRequests(authz -> authz
    //             .requestMatchers(HttpMethod.GET, "/users").hasAuthority("ROLE_ADMIN")
    //             .requestMatchers(HttpMethod.POST, "/login").permitAll()
    //             .requestMatchers(HttpMethod.POST, "/users/create").permitAll()
    //             .anyRequest().authenticated()
    //         )
    //         .addFilter(authenticationFilter) // <-- ESTE sí
    //         .addFilter(new JwtValidationFilter(authenticationManager()))
    //         .csrf(csrf -> csrf.disable())
    //         .cors(cors -> cors.configurationSource(corsConfigurationSource()))
    //         .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //         .build();
    // }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }
}
