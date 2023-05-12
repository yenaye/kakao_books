package com.example.kakaobook.collect;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kakaobook.dao.Books;
import com.example.kakaobook.dao.BooksGroup;

public interface CollectRepository extends JpaRepository<Books, Long> {
    
    // 도서정보 ISBN개수
    Long countByIsbnIn(String[] isbns);
    // 도서정보 저장
    Books save(Books books);

    // 도서그룹 저장
    BooksGroup save(BooksGroup booksWord);
}
