package com.example.kakaobook.collect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CollectController {

    @Autowired
    private CollectService collectService;
    
    @RequestMapping("/")
    public String booksCollect() {
        return "/bookCollect";
    }
    
    /* 검색어의 도서정보 수집 */
    @RequestMapping("/collect/booksCollect")
    @ResponseBody
    public String booksCollectAjax(String kwd) {
        String result = collectService.booksCollect(kwd); // 도서정보 수집
        return result;
    }
}
