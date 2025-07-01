package com.example.toysspring.api;

import java.util.Optional;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import org.apache.commons.lang3.StringUtils;

import com.example.toysspring.domain.User;
import com.example.toysspring.domain.UserSpecs.SearchKey;
import com.example.toysspring.service.UserService;

@SuppressWarnings("unused")
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("")
    public ResponseEntity<? extends BasicResponse> insert(@RequestBody User item) {
        User result = service.insert(item);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("이미 등록된 이메일입니다"));
        }

        return ResponseEntity.ok().body(new CommonResponse<User>(result));
    }

    @PutMapping("{id}")
    public ResponseEntity<? extends BasicResponse> update(@PathVariable Long id, @RequestBody User item) {
        Optional<User> opt = service.findById(id);

        if (!opt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("데이터를 찾을수가 없습니다"));
        }

        User result = service.update(item);
        return ResponseEntity.ok().body(new CommonResponse<User>(result));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<? extends BasicResponse> delete(@PathVariable Long id) {
        Optional<User> opt = service.findById(id);

        if (opt.isPresent()) {
            service.delete(opt.get());
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable Long id) {
        Optional<User> opt = service.findById(id);

        if (!opt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("데이터를 찾을수가 없습니다"));
        }

        User item = opt.get();
        item.setUserPasswd("");

        return ResponseEntity.ok().body(new CommonResponse<User>(item));
    }

    @GetMapping("")
    public ResponseEntity<? extends BasicResponse> findAll(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                           @RequestParam(value = "size", defaultValue = "20", required = false) int size,
                                                           @RequestParam(value = "id", required = false) String id,
                                                           @RequestParam(value = "name", required = false) String name,
                                                           @RequestParam(value = "email", required = false) String email) {
        if (page > 0) {
            page--;
        }
        
        Map<SearchKey, Object> searchKeys = new HashMap<>();
        if (!StringUtils.isEmpty(id)) {
            searchKeys.put(SearchKey.valueOf("ID"), id);
        }

        if (!StringUtils.isEmpty(name)) {
            searchKeys.put(SearchKey.valueOf("NAME"), name);
        }

        if (!StringUtils.isEmpty(email)) {
            searchKeys.put(SearchKey.valueOf("EMAIL"), email);
        }

        if (size == 0) {
            List<User> result = service.findAll();
            return ResponseEntity.ok().body(new CommonResponse<List<User>>(result));
        } else {
            Page<User> result = service.findAll(searchKeys, page, size);
            return ResponseEntity.ok().body(new CommonResponse<Page<User>>(result));
        }
    }
}
