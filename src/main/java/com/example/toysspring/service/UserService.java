package com.example.toysspring.service;

import java.util.Optional;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.PageRequest;
import org.apache.commons.lang3.StringUtils;

import com.example.toysspring.domain.User;
import com.example.toysspring.domain.UserRepository;
import com.example.toysspring.domain.UserSpecs;
import com.example.toysspring.domain.UserSpecs.SearchKey;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // public String sha256(String msg) {
    //    try {
    //        MessageDigest md = MessageDigest.getInstance("SHA-256");
    //        md.update(msg.getBytes());

    //        byte[] bytes = md.digest();
    //        StringBuilder builder = new StringBuilder();
    //        for (byte b: bytes) {
    //            builder.append(String.format("%02x", b));
    //        }
    //        return builder.toString();
    //    } catch (NoSuchAlgorithmException e) {
    //    }

    //    return "";
    // }

    @Transactional
    public User insert(User item) {
        Optional<User> opt = repository.findByUserId(item.getUserId());

        if (opt.isPresent()) {
            return null;
        }

        String passwd = passwordEncoder.encode(item.getUserPasswd());
        item.setUserPasswd(passwd);

        return repository.save(item);
    }

    @Transactional
    public User update(User item) {
        Optional<User> opt = repository.findByUserId(item.getUserId());

        if (!opt.isPresent()) {
            return null;
        }

        User old = opt.get();

        String passwd = item.getUserPasswd();
        if (StringUtils.isEmpty(passwd)) {
            item.setUserPasswd(old.getUserPasswd());
        } else {
            item.setUserPasswd(passwordEncoder.encode(item.getUserPasswd()));
        }

        return repository.save(item);
    }

    @Transactional
    public void delete(User item) {
        repository.delete(item);
    }

    public Optional<User> findById(Long userId) {
        return repository.findByUserId(userId);
    }

    public Optional<User> findByEmail(String userEmail) {
        return repository.findByUserEmail(userEmail);
    }

    public Page<User> findAll(Map<SearchKey, Object> searchKeys, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("userId").descending());

        return searchKeys.isEmpty()
            ? repository.findAll(pageable)
            : repository.findAll(UserSpecs.searchWith(searchKeys), pageable);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User registerUser(String email, String rawPassword) {
        // 1. 이메일 중복 체크, etc.
        // 2. 비밀번호 해싱
        String encoded = passwordEncoder.encode(rawPassword);

        User user = new User();
        user.setUserEmail(email);
        user.setUserPasswd(encoded);

        return repository.save(user);
    }
}
