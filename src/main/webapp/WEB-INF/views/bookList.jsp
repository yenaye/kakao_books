<!DOCTYPE html>
<html>
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container py-5">
        <h4>연관된 도서 목록</h4>
        <hr>
        <div class="row mb-5">
            <div class="col-auto">
                <input type="text" id="kwd1" class="form-control" placeholder="검색 단어1">
            </div>
            <div class="col-auto">
                <input type="text" id="kwd2" class="form-control" placeholder="검색 단어2">
            </div>
            <div class="col-auto">
                <button type="button" class="btn btn-primary" onclick="Self.bookList();">출력</button>
            </div>
        </div>

        <h4>등장 횟수가 가장 많은 단어 TOP10 목록</h4>
        <hr>
        <div class="row mb-5">
            <button type="button" class="btn btn-primary" onclick="Self.bookCountTopList();">출력</button>
        </div>

        <h4>제목에 포함된 도서 단어 목록</h4>
        <hr>
        <div class="row mb-5">
            <div class="col-auto">
                <input type="text" id="title" class="form-control" placeholder="도서 제목">
            </div>
            <div class="col-auto">
                <button type="button" class="btn btn-primary" onclick="Self.bookWordApi();">출력</button>
            </div>
        </div>

        <h4>특정 도서의 상세 정보</h4>
        <hr>
        <div class="row mb-5">
            <div class="col-auto">
                <input type="text" id="info_id" class="form-control" placeholder="도서ID">
            </div>
            <div class="col-auto">
                <button type="button" class="btn btn-primary" onclick="Self.bookInfoApi();">출력</button>
            </div>
        </div>

        <h4>특정 도서 내에 단어 추가</h4>
        <hr>
        <div class="row mb-5">
            <div class="col-auto">
                <input type="text" id="add_id" class="form-control" placeholder="도서ID">
            </div>
            <div class="col-auto">
                <input type="text" id="add_word" class="form-control" placeholder="추가 단어">
            </div>
            <div class="col-auto">
                <button type="button" class="btn btn-primary" onclick="Self.bookWordInsertApi();">추가</button>
            </div>
        </div>

        <h4>특정 도서 내에 단어 삭제</h4>
        <hr>
        <div class="row">
            <div class="col-auto">
                <input type="text" id="del_id" class="form-control" placeholder="도서ID">
            </div>
            <div class="col-auto">
                <input type="text" id="del_word" class="form-control" placeholder="삭제 단어">
            </div>
            <div class="col-auto">
                <button type="button" class="btn btn-primary" onclick="Self.bookWordDeleteApi();">삭제</button>
            </div>
        </div>
    </div>
</body>

<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script>
const Self = {
    /* 연관된 도서 목록 */
	bookList: function() {
        const kwd1 = $.trim($('#kwd1').val()); // 검색단어1
        const kwd2 = $.trim($('#kwd2').val()); // 검색단어2
        if (kwd1 == '' || kwd1 == null) {
            alert('검색 단어1을 입력해주세요.');
            $('#kwd1').focus();
            return;
        } else if (kwd2 == '' || kwd2 == null) {
            alert('검색 단어2를 입력해주세요.');
            $('#kwd2').focus();
            return;
        }
        $.ajax({
            url: '/list/bookList',
            type : 'post',
            data: {
                'kwd1': kwd1,
                'kwd2': kwd2
            },
            success: function(result) {
                console.log(result);
                alert(JSON.stringify(result));
            },
			error:function(xhr, option, error){
                alert('오류가 발생하였습니다.');
				console.log(xhr.status); //오류코드
				console.log(error); //오류내용
			}
        });
	},

    /* 등장 횟수가 가장 많은 단어 TOP10 목록 */
	bookCountTopList: function() {
        $.ajax({
            url: '/list/bookCountTopList',
            type : 'post',
            data: {},
            success: function(result) {
                console.log(result);
                alert(JSON.stringify(result));
            },
			error:function(xhr, option, error){
                alert('오류가 발생하였습니다.');
				console.log(xhr.status); //오류코드
				console.log(error); //오류내용
			}
        });
	},

    /* 제목에 포함된 도서 단어 목록 API */
	bookWordApi: function() {
        const title = $.trim($('#title').val()); // 도서제목
        if (title == '' || title == null) {
            alert('도서제목을 입력해주세요.');
            $('#title').focus();
            return;
        }
        $.ajax({
            url: '/api/bookWordApi',
            type : 'post',
            data: {
                'type': 'normal',
                'title': title,
                'page': 1,
                'size': 100
            },
            success: function(result) {
                console.log(result);
                alert(JSON.stringify(result));
            },
			error:function(xhr, option, error){
                alert('오류가 발생하였습니다.');
				console.log(xhr.status); //오류코드
				console.log(error); //오류내용
			}
        });
	},
	
    /* 특정 도서의 상세 정보 API */
	bookInfoApi: function() {
        const id = $.trim($('#info_id').val()).replace(/\^D/, ''); // 도서ID
        if (id == '' || id == null) {
            alert('도서ID를 입력해주세요.');
            $('#info_id').focus();
            return;
        }
        $.ajax({
            url: '/api/bookInfoApi',
            type : 'post',
            data: {
                'id': id
            },
            success: function(result) {
                console.log(result);
                alert(JSON.stringify(result));
            },
			error:function(xhr, option, error){
                alert('오류가 발생하였습니다.');
				console.log(xhr.status); //오류코드
				console.log(error); //오류내용
			}
        });
	},
	
    /* 특정 도서 내에 단어 추가 API */
	bookWordInsertApi: function() {
        const id = $.trim($('#add_id').val()).replace(/\^D/, ''); // 도서ID
        const word = $.trim($('#add_word').val()); // 단어
        if (id == '' || id == null) {
            alert('도서ID를 입력해주세요.');
            $('#add_id').focus();
            return;
        } else if (word == '' || word == null) {
            alert('단어를 입력해주세요.');
            $('#add_word').focus();
            return;
        }
        $.ajax({
            url: '/api/bookWordInsertApi',
            type : 'post',
            data: {
                'id': id,
                'word': word
            },
            success: function(result) {
                if (result == 'SUCCESS') {
                    alert('해당 도서 내에 새로운 단어를 추가하였습니다.');
                } else {
                    alert('오류가 발생하였습니다.');
                }
            },
			error:function(xhr, option, error){
                alert('오류가 발생하였습니다.');
				console.log(xhr.status); //오류코드
				console.log(error); //오류내용
			}
        });
	},
	
    /* 특정 도서 내에 단어 삭제 API */
	bookWordDeleteApi: function() {
        const id = $.trim($('#del_id').val()).replace(/\^D/, ''); // 도서ID
        const word = $.trim($('#del_word').val()); // 단어
        if (id == '' || id == null) {
            alert('도서ID를 입력해주세요.');
            $('#del_id').focus();
            return;
        } else if (word == '' || word == null) {
            alert('단어를 입력해주세요.');
            $('#del_word').focus();
            return;
        }
        $.ajax({
            url: '/api/bookWordDeleteApi',
            type : 'post',
            data: {
                'id': id,
                'word': word
            },
            success: function(result) {
                if (result == 'SUCCESS') {
                    alert('해당 도서 내에 단어를 삭제하였습니다.');
                } else {
                    alert('오류가 발생하였습니다.');
                }
            },
			error:function(xhr, option, error){
                alert('오류가 발생하였습니다.');
				console.log(xhr.status); //오류코드
				console.log(error); //오류내용
			}
        });
	}
}
</script>
</html>