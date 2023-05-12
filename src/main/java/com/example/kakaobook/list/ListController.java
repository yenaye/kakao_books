package com.example.kakaobook.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ListController {

    @Autowired
    private ListService listService;
    
    @RequestMapping("/list")
    public String bookList() {
        return "/bookList";
    }
    
    /* 두 검색어의 연관된 도서 목록 */
    @RequestMapping(value = "/list/bookList")
    @ResponseBody
    public List<Map<String, Object>> bookListAjax(String kwd1, String kwd2) {
        List<Map<String, Object>> result = new ArrayList<>();
        result = listService.bookList(kwd1, kwd2); // 연관된 도서 목록
        return result;
    }
    
    /* 등장 횟수가 가장 많은 단어 TOP10 목록 */
    @RequestMapping(value = "/list/bookCountTopList")
    @ResponseBody
    public List<Map<String, Object>> bookCountTopListAjax() {
        List<Map<String, Object>> result = new ArrayList<>();
        result = listService.bookCountTopList(); // 등장 횟수 많은 단어 TOP10 연관단어 목록
        return result;
    }
}
