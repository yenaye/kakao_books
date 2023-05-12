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
    <div class="container p-3">
        <h4>도서 정보 수집</h4>
        <hr>
        <div class="row">
            <div class="col-auto">
                <input type="text" id="collect_kwd" class="form-control" placeholder="수집 검색어">
            </div>
            <div class="col-auto">
                <button type="button" class="btn btn-primary" onclick="Self.booksCollect();">수집</button>
            </div>
        </div>
    </div>
</body>

<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script>
const Self = {
    /* 도서정보 수집 */
	booksCollect: function() {
        const kwd = $.trim($('#collect_kwd').val()); // 수집 검색어
        if (kwd == '' || kwd == null) {
            alert('수집 검색어를 입력해주세요.');
            $('#collect_kwd').focus();
            return;
        }
        $.ajax({
            url: '/collect/booksCollect',
            type : 'post',
            data: {
                'kwd': kwd
            },
            success: function(result) {
                if (result == 'SUCCESS') {
                    alert('도서 정보 수집이 완료 되었습니다.');
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