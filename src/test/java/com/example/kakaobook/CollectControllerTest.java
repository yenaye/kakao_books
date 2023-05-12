package com.example.kakaobook;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.kakaobook.collect.CollectController;

@SpringBootTest
class CollectControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(CollectControllerTest.class);

    @Autowired
    private CollectController collectController;
    
    @DisplayName("도서 정보 수집 테스트")
    @Test
    void booksCollectAjaxTest() {
        String result = collectController.booksCollectAjax("고양이");
        logger.info("::::: 도서 정보 수집 테스트 ::::: " + result);
    }
}
