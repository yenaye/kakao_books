package com.example.kakaobook;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.kakaobook.list.ListController;

@SpringBootTest
class ListControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(ListControllerTest.class);

    @Autowired
    private ListController listController;
    
    @DisplayName("연관된 도서 목록 테스트")
    @Test
    void bookListAjaxTest() {
        List<Map<String, Object>> result = listController.bookListAjax("강아지", "고양이");
        logger.info("::::: 연관된 도서 목록 테스트 :::::");
        logger.info(Arrays.toString(result.toArray()));
    }
    
    @DisplayName("등장 횟수가 가장 많은 단어 TOP10 목록 테스트")
    @Test
    void bookCountTopListAjaxTest() {
        List<Map<String, Object>> result = listController.bookCountTopListAjax();
        logger.info("::::: 등장 횟수가 가장 많은 단어 TOP10 목록 테스트 :::::");
        logger.info(Arrays.toString(result.toArray()));
    }
}
