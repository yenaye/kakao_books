package com.example.kakaobook.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;
    
    /* 제목에 포함된 도서 단어 목록 API */
    @RequestMapping(value = "/bookWordApi")
    public Map<String, Object> bookWordApi(String type, String title, int page, int size) {
        Map<String, Object> result = new HashMap<>();
        result = apiService.bookWordApi(type, title, page, size); // 도서단어 목록 API
        return result;
    }
    
    /* 특정 도서의 상세 정보 API */
    @RequestMapping(value = "/bookInfoApi")
    public Map<String, Object> bookInfoApi(Long id) {
        Map<String, Object> result = new HashMap<>();
        result = apiService.bookInfoApi(id); // 도서상세정보 목록 API
        return result;
    }
    
    /* 특정 도서 내에 단어 추가 API */
    @RequestMapping(value = "/bookWordInsertApi")
    public String bookWordInsertApi(Long id, String word) {
        String result = apiService.bookWordInsertApi(id, word); // 도서단어 추가 API
        return result;
    }
    
    /* 특정 도서 내에 단어 삭제 API */
    @RequestMapping(value = "/bookWordDeleteApi")
    public String bookWordDeleteApi(Long id, String word) {
        String result = apiService.bookWordDeleteApi(id, word); // 도서단어 삭제 API
        return result;
    }
}
