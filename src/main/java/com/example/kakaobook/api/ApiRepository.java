package com.example.kakaobook.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.kakaobook.dao.BooksGroup;

public interface ApiRepository extends JpaRepository<BooksGroup, Long> {
    
    // 도서제목에 검색어가 포함되어 있는 도서단어 개수
    // 아래 리스트에서 도서단어를 중복제거하기때문에 COUNT시 DISTINCT로 단어 중복제거
    @Query(value = " SELECT COUNT(DISTINCT B.WORD) AS CNT \n" +
                    "  FROM BOOKS AS A \n" +
                    "  LEFT JOIN BOOKS_GROUP AS B \n" +
                    "    ON A.ID = B.BOOKS_ID \n" +
                    " WHERE A.TITLE LIKE CONCAT('%', :title, '%') ", nativeQuery = true)
    int findBookWordListCount(@Param("title") String title);
    
    // 도서제목에 검색어가 포함되어 있는 도서단어 리스트
    @Query(value = " SELECT B.WORD \n" +
                    "  FROM BOOKS AS A \n" +
                    "  LEFT JOIN BOOKS_GROUP AS B \n" +
                    "    ON A.ID = B.BOOKS_ID \n" +
                    " WHERE A.TITLE LIKE CONCAT('%', :title, '%') \n" +
                    " GROUP BY B.WORD \n" +
                    " ORDER BY B.WORD ASC \n" +
                    " LIMIT :page, :size ", nativeQuery = true)
    List<String> findBookWordList(@Param("title") String title, @Param("page") int page, @Param("size") int size);
    
    // 해당 도서ID의 도서제목
    @Query(value = " SELECT TITLE \n" +
                    "  FROM BOOKS \n" +
                    " WHERE ID = :id ", nativeQuery = true)
    String findBookInfoTitle(@Param("id") Long id);

    // 해당 도서ID의 도서그룹 정보 리스트
    @Query(value = " SELECT GROUP_NUM, WORD \n" +
                    "  FROM BOOKS_GROUP \n" +
                    " WHERE BOOKS_ID = :id ", nativeQuery = true)
    List<Map<String, Object>> findBooksGroupList(@Param("id") Long id);

    // 추가할 단어의 그룹번호
    // 도서그룹에서 해당 도서ID에서 추가할 단어와 동일한 단어가 있는 그룹번호는 제외
    // 도서그룹에서 해당 도서ID의 연관관계를 가진 그룹번호와 마지막 그룹번호+1를 조회 후, 연관관계를 가진 그룹번호가 있고 단어 개수가 10개미만이라면 연관그룹번호를 아니면 마지막 그룹번호+1를 조회
    @Query(value = " SELECT CASE WHEN LIKE_GROUP_NUM != 0 AND COUNT(1) < 10 \n" +
                    "            THEN LIKE_GROUP_NUM \n" +
                    "            ELSE MAX_GROUP_NUM \n" +
                    "             END AS GROUP_NUM \n" +
                    "  FROM ( \n" +
                    "        SELECT CASE WHEN WORD LIKE CONCAT(:firstLetter, '%') \n" +
                    "                    THEN GROUP_NUM \n" +
                    "                    ELSE 0 \n" +
                    "                     END AS LIKE_GROUP_NUM, \n" +
                    "               MAX(GROUP_NUM) OVER()+1 AS MAX_GROUP_NUM \n" +
                    "          FROM BOOKS_GROUP \n" +
                    "         WHERE BOOKS_ID = :id \n" +
                    "           AND GROUP_NUM NOT IN ( SELECT GROUP_NUM FROM BOOKS_GROUP WHERE BOOKS_ID = :id AND WORD = :word ) \n" +
                    "       ) \n" +
                    " GROUP BY LIKE_GROUP_NUM \n" +
                    " ORDER BY LIKE_GROUP_NUM DESC \n" +
                    " LIMIT 1 ", nativeQuery = true)
    int findBooksGroupNum(@Param("id") Long id, @Param("word") String word, @Param("firstLetter") String firstLetter);

    // 해당 도서ID의 단어그룹 저장
    BooksGroup save(BooksGroup booksWord);

    // 해당 도서ID의 단어 삭제
    @Transactional
    void deleteByBooksIdAndWord(Long id, String word);
}
