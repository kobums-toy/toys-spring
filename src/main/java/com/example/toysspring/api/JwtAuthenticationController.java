package com.example.toysspring.api;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.toysspring.config.JwtTokenUtil;
import com.example.toysspring.model.JwtResponse;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtInMemoryUserDetailsService;

    /**
     * GET 방식으로 로그인 예시
     * 예: GET /api/jwt?email=user@example.com&passwd=1234
     */
    @GetMapping("/api/jwt")
    public ResponseEntity<?> createAuthenticationToken(
        @RequestParam String email,
        @RequestParam String passwd
    ) throws Exception {
        authenticate(email, passwd);

        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(email);

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String email, String passwd) throws Exception {
        Objects.requireNonNull(email);
        Objects.requireNonNull(passwd);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, passwd));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}