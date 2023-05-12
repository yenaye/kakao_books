package com.example.kakaobook.list;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.kakaobook.dao.Books;

public interface ListRepository extends JpaRepository<Books, Long> {
    
    // 연관 도서 리스트
    @Query(value = " SELECT B.ISBN, MAX(B.TITLE) AS TITLE \n" +
                    "  FROM BOOKS_GROUP AS A \n" +
                    " INNER JOIN BOOKS AS B \n" +
                    "    ON A.BOOKS_ID = B.ID \n" +
                    " WHERE A.WORD IN (:kwd1, :kwd2) \n" +
                    " GROUP BY B.ISBN ", nativeQuery = true)
    List<Map<String, Object>> findBookList(@Param("kwd1") String kwd1, @Param("kwd2") String kwd2);

    // 도서그룹 TOP10 단어 리스트
    @Query(value = " SELECT WORD, COUNT(1) AS CNT \n" +
                    "  FROM BOOKS_GROUP \n" +
                    " GROUP BY WORD \n" +
                    " ORDER BY CNT DESC \n" +
                    " LIMIT 10 ", nativeQuery = true)
    List<Map<String, Object>> findBookCountTopList();

    // 도서그룹 TOP10 단어의 연관단어 리스트
    // 셀프조인으로 도서그룹 TOP10 단어의 도서ID와 그룹번호의 단어 조회(검색 단어는 결과에서 제외)
    @Query(value = " SELECT A.WORD \n" +
                    "  FROM BOOKS_GROUP AS A \n" +
                    " INNER JOIN BOOKS_GROUP AS B \n" +
                    "    ON B.WORD = :word \n" +
                    "   AND A.BOOKS_ID = B.BOOKS_ID \n" +
                    "   AND A.GROUP_NUM = B.GROUP_NUM \n" +
                    " WHERE A.WORD != :word ", nativeQuery = true)
    List<String> findBookCountTopWordList(@Param("word") String word);
}
