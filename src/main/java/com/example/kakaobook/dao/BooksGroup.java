package com.example.kakaobook.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BooksGroup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 도서그룹ID

    @Column(name = "books_id")
    private Long booksId; // 도서ID

    @Column(name = "group_num")
    private Integer groupNum; // 그룹번호

    private String word; // 단어

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBooksId() {
        return this.booksId;
    }

    public void setBooks_id(Long booksId) {
        this.booksId = booksId;
    }

    public Integer getGroupNum() {
        return this.groupNum;
    }

    public void setGroup_num(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }

}
