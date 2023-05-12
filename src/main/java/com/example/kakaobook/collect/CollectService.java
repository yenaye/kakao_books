package com.example.kakaobook.collect;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.kakaobook.dao.Books;
import com.example.kakaobook.dao.BooksGroup;

@Service
public class CollectService {

    @Autowired
    private CollectRepository collectRepository;
    
    /* 카카오 책 검색하기 API */
    public JSONArray kakaoApiSearchBooks(String kwd, int page) throws Exception {
        JSONArray jsonArray = new JSONArray(); // 결과 값

        RestTemplate restTemplate = new RestTemplate(); // Rest방식 API를 호출할 수 있는 Spring 내장 클래스
        HttpHeaders headers = new HttpHeaders(); // 요청 헤더

        String restApiKey = "REST API Key"; // REST API Key
        String uri = "https://dapi.kakao.com/v3/search/book"; // 호출할 URI
        String query = URLEncoder.encode(kwd, "UTF-8"); // 검색어 Encode(한글은 깨지기 때문에)
        int size = 50; // 한페이지에 보여지는 문서개수(50이 최대치)
        String param = "?query=" + query + "&page=" + page + "&size=" + size; // Parameter

        headers.add("Authorization", "KakaoAK " + restApiKey); // 사용자 증명을 위한 REST API Key 추가

        URI url = URI.create(uri + param);
        RequestEntity<String> request = new RequestEntity<>(headers, HttpMethod.GET, url); // 요청
        ResponseEntity<String> response = restTemplate.exchange(request, String.class); // 응답
        
        // JSON 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
        jsonArray = (JSONArray) jsonObject.get("documents");

        return jsonArray;
    }

    /* 도서정보 수집 */
    @Transactional
    public String booksCollect(String kwd) {
        int page = 1; // 페이지번호
        int size = 0; // 수집된 문서개수(jsonArray 사이즈)

        try {
            while (true) {
                JSONArray jsonArray = this.kakaoApiSearchBooks(kwd, page); // 카카오 책 검색하기 API 결과 값
                size = jsonArray.size(); // 수집된 문서개수(jsonArray 사이즈)

                // 수집된 문서가 없거나, 카카오 책 검색 페이지가 10페이지면 나가기
                if (size == 0 || page == 10) {
                    break;
                }
    
                for (int i=0; i<size; i++) {
                    JSONObject document = (JSONObject) jsonArray.get(i); // 도서정보 JSON
        
                    String[] isbns = document.get("isbn").toString().split(" "); // ISBN(ISBN10 또는 ISBN13 중 하나 이상 포함, 공백으로 구분)
                    String[] newIsbns = Arrays.stream(isbns).filter(d -> !"".equals(d)).toArray(String[]::new); // 빈 값 필터링한 ISBN
                    String title = document.get("title").toString(); // 도서제목
                    String contents = document.get("contents").toString(); // 도서소개
    
                    // 동일한 ISBN을 가진 도서정보 제외
                    Long isbnCnt = collectRepository.countByIsbnIn(newIsbns); // ISBN개수
                    if (isbnCnt > 0) {
                        continue;
                    }
    
                    // 도서정보 저장
                    Books books = new Books(); // 도서정보
                    books.setIsbn(newIsbns[0]); // 첫번째 ISBN
                    books.setTitle(title); // 도서제목
                    Long booksId = collectRepository.save(books).getId(); // 도서정보 저장후 도서ID return
    
                    // 도서그룹 저장(단어의 첫번째 글자가 동일하면 동일 그룹으로 묶음)
                    String[] words = (title + " " + contents).toLowerCase()
                                    .replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-z0-9\\s]", "")
                                    .split(" "); // 소문자 변환 및 한글,영어,숫자,공백을 제외한 나머지(특수문자) 제거 후 공백으로 단어 분리
                    String[] newWords = Arrays.stream(words)
                                        .filter(d -> !"".equals(d.trim()))
                                        .distinct()
                                        .sorted()
                                        .toArray(String[]::new); // 빈 값 필터링 및 중복 값 제거 후 정렬한 단어들
                    BooksGroup booksGroup = new BooksGroup(); // 도서그룹
                    int groupNum = 0; // 그룹번호
                    int groupWordCnt = 0; // 그룹단어개수(한 그룹당 최대 10개 단어)
                    String firstLetter = ""; // 첫글자
                    for (String word : newWords) {
                        // 첫번째 글자가 다르거나, 그룹에 10개(최대치) 단어가 다 찼을 시 새 그룹으로 지정
                        if (!firstLetter.equals(word.substring(0, 1)) || groupWordCnt==10) {
                            groupNum++;
                            groupWordCnt = 0;
                        }
                        groupWordCnt++; // 그룹단어개수
    
                        booksGroup = new BooksGroup(); // 도서그룹
                        booksGroup.setBooks_id(booksId); // 도서ID
                        booksGroup.setGroup_num(groupNum); // 그룹번호
                        booksGroup.setWord(word); // 단어
                        collectRepository.save(booksGroup); // 도서그룹 저장
    
                        firstLetter = word.substring(0, 1); // 첫글자
                    }
                }
                page++; // 페이지번호
            }

            return "SUCCESS";
        } catch (Exception e) {
            System.out.println(e);
            return "ERROR";
        }
    }
}
