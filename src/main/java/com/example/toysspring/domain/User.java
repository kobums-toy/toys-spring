package com.example.toysspring.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name="user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private Long userId;

    @Column(name = "u_email", length = 255)
    private String userEmail;

    @Column(name = "u_name", length = 255)
    private String userName;

    @Column(name = "u_passwd", length = 255)
    private String userPasswd;

    @CurrentTimestamp
    @Column(name = "u_date")
    private Timestamp userDate;
}
