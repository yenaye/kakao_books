package com.example.kakaobook.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kakaobook.dao.BooksGroup;

@Service
public class ApiService {

    @Autowired
    private ApiRepository apiRepository;

    /* 도서단어 목록 API */
    public Map<String, Object> bookWordApi(String type, String title, int page, int size) {
        Map<String, Object> result = new HashMap<>();

        int count = apiRepository.findBookWordListCount(title); // 도서제목에 검색어가 포함되어 있는 도서단어 개수

        if ("normal".equals(type)) {
            result.put("totalCount", count); // 총개수
        } else if ("slice".equals(type)) {
            String nextPageYn = count-(page*size) > size ? "Y" : "N";
            result.put("nextPageYn", nextPageYn); // 다음 페이지 여부
        }
        List<String> list = apiRepository.findBookWordList(title, page, size); // 도서제목에 검색어가 포함되어 있는 도서단어 리스트

        result.put("list", list); // 단어목록

        return result;
    }

    /* 도서상세정보 목록 API */
    public Map<String, Object> bookInfoApi(Long id) {
        Map<String, Object> result = new HashMap<>();

        String title = apiRepository.findBookInfoTitle(id); // 해당 도서ID의 도서제목
        List<Map<String, Object>> list = apiRepository.findBooksGroupList(id); // 해당 도서ID의 도서그룹 정보 리스트

        result.put("title", title); // 도서제목
        result.put("infoList", list); // 상세정보목록

        return result;
    }

    /* 도서단어 추가 API */
    public String bookWordInsertApi(Long id, String word) {
        try {
            String firstLetter = word.substring(0, 1); // 추가할 단어의 첫글자
            int groupNum = apiRepository.findBooksGroupNum(id, word, firstLetter); // 추가할 단어의 그룹번호

            BooksGroup booksGroup = new BooksGroup(); // 도서그룹
            booksGroup.setBooks_id(id); // 도서ID
            booksGroup.setGroup_num(groupNum); // 그룹번호
            booksGroup.setWord(word); // 단어

            apiRepository.save(booksGroup); // 해당 도서ID의 단어그룹 저장
            return "SUCCESS";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    /* 도서단어 삭제 API */
    public String bookWordDeleteApi(Long id, String word) {
        try {
            apiRepository.deleteByBooksIdAndWord(id, word); // 해당 도서ID의 단어 삭제
            return "SUCCESS";
        } catch (Exception e) {
            System.out.println(e);
            return "ERROR";
        }
    }
}
