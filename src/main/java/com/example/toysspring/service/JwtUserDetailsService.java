package com.example.toysspring.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.toysspring.domain.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
  UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("loadUserByUsername called with username={}", email);
		com.example.toysspring.domain.User userEntity = repository.findByUserEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		return new User(
			userEntity.getUserEmail(),        // username
			userEntity.getUserPasswd(),      // bcrypt-hash
			new ArrayList<>()            // 권한
		);
		// if ("apple44@naver.com".equals(email)) {
		// 	String bcryptHash = "$2a$10$T.WU.Bm.vW54BQ9P2aZ2BOrkyhAKB61DMdCbtl8iFv4EDhneeQRw6";

    //   log.info("Found user => username={}, password={}", email, bcryptHash);

		// 	return new User("apple44@naver.com", "$2a$10$T.WU.Bm.vW54BQ9P2aZ2BOrkyhAKB61DMdCbtl8iFv4EDhneeQRw6",
		// 			new ArrayList<>());
		// } else {
		// 	throw new UsernameNotFoundException("User not found with email: " + email);
		// }
	}

}
