package com.example.toysspring.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name="board_tb")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "b_id")
    private Long boardId;

    @Column(name = "b_title", length = 255)
    private String boardTitle;

    @Column(name = "b_content", length = 255)
    private String boardContent;

    @Column(name = "b_img", length = 255)
    private String BoardImg;

    @Column(name = "b_user")
    private Long userId;

    @CurrentTimestamp
    @Column(name = "b_date")
    private Timestamp userDate;
}
