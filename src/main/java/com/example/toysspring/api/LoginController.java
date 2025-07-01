package com.example.toysspring.api;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.toysspring.domain.User;
import com.example.toysspring.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    UserService service;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<? extends BasicResponse> login(@RequestParam(value = "email") String email, @RequestParam(value = "passwd") String passwd) {
        Optional<User> opt = service.findByEmail(email);
        if (!opt.isPresent()) {
            log.info("not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("사용자를 찾을수가 없습니다"));
        }

        User user = opt.get();

        log.info("passwd = " + passwd);
        log.info("user.passwd = " + user.getUserPasswd());

        return ResponseEntity.ok().body(new CommonResponse<User>(user));

        //return user;

        /*
        if (user.getPasswd().equals(passwd)) {
            return user;
        } else {
            log.info("wrong passwd");
            return new User();
        }
        */
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ResponseEntity<? extends BasicResponse> logout() {
        return ResponseEntity.noContent().build();
    }
}
