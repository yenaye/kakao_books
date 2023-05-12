package com.example.kakaobook.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListService {

    @Autowired
    private ListRepository listRepository;

    /* 연관된 도서 목록 */
    public List<Map<String, Object>> bookList(String kwd1, String kwd2) {
        return listRepository.findBookList(kwd1, kwd2); // 연관 도서 리스트
    }

    /* 등장 횟수 많은 단어 TOP10 연관단어 목록 */
    public List<Map<String, Object>> bookCountTopList() {
        List<Map<String, Object>> result = new ArrayList<>(); // 결과 값

        List<Map<String, Object>> bookCountTopList = listRepository.findBookCountTopList(); // 도서그룹 TOP10 단어 리스트
        Map<String, Object> map = new HashMap<>(); // 도서그룹 TOP10 단어 Map
        int rank = 1; // 순위

        for (Map<String, Object> bookCountTop : bookCountTopList) {
            String word = bookCountTop.get("WORD").toString(); // 단어
            int count = Integer.parseInt(bookCountTop.get("CNT").toString()); // 등장 횟수

            List<String> bookCountTopWordList =  listRepository.findBookCountTopWordList(word);  // 도서그룹 TOP10 단어의 연관단어 리스트

            map = new HashMap<>();
            map.put("RANK", rank++); // 순위
            map.put("WORD", word); // TOP10 단어
            map.put("COUNT", count); // 등장 횟수
            map.put("RELATION_WORD_LIST", bookCountTopWordList); // 연관단어 리스트
            result.add(map);
        }
        return result;
    }
}
