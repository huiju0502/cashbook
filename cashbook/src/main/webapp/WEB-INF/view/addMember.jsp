<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		// 시작시 id 입력 폼에 포커스
		$('#memberId').focus();
		
		// memberId유효성 체크
		$('#memberId').blur(function() { // blur : 커서를 읽음
			let id = $(this).val();
			if ($('#memberId').val().length < 4) {
				$('#memberIdMsg').text('아이디는 4자이상이어야 합니다');
				$('#memberId').focus();
			} else if (!/^[a-z]+$/.test(id) && !/^[a-z0-9]+$/.test(id) && !/^[0-9]+$/.test(id)) {
				$('#memberIdMsg').text('아이디는 영문 소문자와 숫자로만 구성되어야 합니다');
				$('#memberId').focus();
			} else {
				console.log($('#memberId').val()); 
				$('#memberIdMsg').text(''); // 텍스트를 지움
				$('#memberPw').focus();
			}
		});
		
		// memberPw유효성 체크
		$('#memberPw').blur(function(){
			if ($('#memberPw').val().length < 4) {
				$('#memberPwMsg').text('비밀번호는 4자이상이어야 합니다');
				$('#memberPw').focus();
			} else {
				$('#memberPwMsg').text('');
				$('#memberPwCk').focus();
			}
		});
		
		// memberPwCk유효성 체크
		$('#memberPwCk').blur(function(){
			if ($('#memberPwCk').val() != $('#memberPw').val()) {
				$('#memberPwCkMsg').text('비밀번호를 확인하세요');
				$('#memberPwCk').focus();
			} else {
				$('#memberPwCkMsg').text('');
			}
		});
		
		// 유효성 체크 함수
	    function validateForm() {	
	    	let allCheck = true;
	    	
	    	// memberId유효성 체크
			if ($('#memberId').val().length < 4) { 
				$('#memberId').focus();
				allCheck = false;
			}
	    	
			// memberPw유효성 체크
			if ($('#memberPw').val().length < 4) { 
				$('#memberPw').focus();
				allCheck = false;
			} 
			
			// memberPwCk유효성 체크
			if ($('#memberPwCk').val().length < 4) { 
				$('#memberPwCk').focus();
				allCheck = false;
			}
			
			// pw/ckPw 일치여부 체크
			if ($('#memberPwCk').val() != $('#memberPw').val()) {
				allCheck = false;
			}
			
			return allCheck;
		}
	    $('#signinBtn').click(function(e) {
	        e.preventDefault(); // 기본 동작 방지

	        if (validateForm()) {
	            $('#signinForm').submit();
	            alert('회원가입이 완료되었습니다');
	        }
	    });
	});
</script>
</head>
<body>
	<h1>회원가입</h1>
	<form id="signinForm" action="${pageContext.request.contextPath}/addMember" method="post">
		<table border="1">
			<tr>
				<td>아이디</td>
				<td>
					<input type="text" name="memberId" id="memberId" placeholder="아이디를 입력하세요">
					<span id="memberIdMsg" class="msg"></span>
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td>
					<input type="password" name="memberPw" id="memberPw" placeholder="4자 이상의 비밀번호를 입력하세요">
					<span id="memberPwMsg" class="msg"></span>
				</td>
			</tr>
			<tr>
				<td>비밀번호 확인</td>
				<td>
					<input type="password" name="memberPwCk" id="memberPwCk" placeholder="비밀번호를 한번 더 입력하세요">
					<span id="memberPwCkMsg" class="msg"></span>
				</td>
			</tr>
		</table>
		<button type="button" id="signinBtn">회원가입</button>
	</form>
	
	<div>
	<jsp:include page="/WEB-INF/inc/copyright.jsp"></jsp:include>
	</div>
</body>
</html>