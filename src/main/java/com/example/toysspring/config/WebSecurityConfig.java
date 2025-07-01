package com.example.toysspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    
     // 아래 3개 메서드만 남긴다
     @SuppressWarnings("removal")
    @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http.csrf().disable()
             .authorizeHttpRequests(auth -> auth
                 .requestMatchers("/api/jwt", "/api/login").permitAll()
                 .anyRequest().authenticated()
             )
             .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
             .and()
             .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
 
         http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
         return http.build();
     }
 
     @Bean
     public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
         AuthenticationManagerBuilder authBuilder =
                 http.getSharedObject(AuthenticationManagerBuilder.class);
 
         authBuilder
             .userDetailsService(jwtUserDetailsService)
             .passwordEncoder(passwordEncoder());
 
         return authBuilder.build();
     }
 
     @Bean
     public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
     }
}
