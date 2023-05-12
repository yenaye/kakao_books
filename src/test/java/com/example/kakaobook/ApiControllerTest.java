package com.example.kakaobook;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.kakaobook.api.ApiController;

@SpringBootTest
class ApiControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(ApiControllerTest.class);

    @Autowired
    private ApiController apiController;
    
    @DisplayName("제목에 포함된 도서 단어 목록 테스트")
    @Test
    void bookWordApiTest() {
        Map<String, Object> result = apiController.bookWordApi("slice", "감자", 1, 50);
        logger.info("::::: 제목에 포함된 도서 단어 목록 테스트 :::::");
        logger.info(result.toString());
    }
    
    @DisplayName("특정 도서의 상세 정보 테스트")
    @Test
    void bookInfoApiTest() {
        Map<String, Object> result = apiController.bookInfoApi((long) 38605);
        logger.info("::::: 특정 도서의 상세 정보 테스트 :::::");
        logger.info(result.toString());
    }
    
    @DisplayName("특정 도서 내에 단어 추가 테스트")
    @Test
    void bookWordInsertApiTest() {
        String result = apiController.bookWordInsertApi((long) 38605, "조그마한");
        logger.info("::::: 특정 도서 내에 단어 추가 테스트 ::::: " + result);
    }
    
    @DisplayName("특정 도서 내에 단어 삭제 테스트")
    @Test
    void bookWordDeleteApiTest() {
        String result = apiController.bookWordDeleteApi((long) 38605, "흘러");
        logger.info("::::: 특정 도서 내에 단어 삭제 테스트 ::::: " + result);
    }
}
