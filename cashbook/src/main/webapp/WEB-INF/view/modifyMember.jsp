<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		// 시작시 pw 입력 폼에 포커스
		$('#pw').focus();
		
		// pw유효성 체크
		$('#pw').blur(function(){
			if ($('#pw').val().length < 1) {
				$('#pwMsg').text('비밀번호를 입력해주세요');
				$('#pw').focus();
			} else {
				$('#pwMsg').text('');
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
				$('memberPwCkMsg').text('');
			}
		});
		
		// 유효성 체크 함수
	    function validateForm() {	
	    	let allCheck = true;
	    	
	    	// pw유효성 체크
			if ($('#pw').val().length < 4) { 
				$('#pw').focus();
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
			// memberPw/emberPw유효성체크
			if ($('#memberPwCk').val() != $('#memberPw').val()) {
				allCheck = false;
			}
			
			return allCheck;
		}
	    $('#modifyBtn').click(function(e) {
	        e.preventDefault(); // 기본 동작 방지

	        if (validateForm()) {
	            $('#modifyForm').submit();
	            alert('비밀번호 변경이 완료되었습니다');
	        }
	    });
		
	});
</script>
</head>
<body>
	<!-- 메인메뉴 -->
	<div>
		<jsp:include page="/WEB-INF/inc/mainmenu.jsp"></jsp:include>
	</div>
	<h1>비밀번호 수정</h1>
	<form id="modifyForm" action="${pageContext.request.contextPath}/modifyMember" method="post">
		<table border="1">
			<tr>
				<td>현재 비밀번호</td>
				<td>
					<input type="password" name="pw" id="pw" placeholder="현재 비밀번호를 입력하세요">
					<span id="pwMsg" class="msg"></span>
				</td>
			</tr>
			<tr>
				<td>새로운 비밀번호</td>
				<td>
					<input type="password" name="memberPw" id="memberPw"placeholder="4자 이상의 새로운 비밀번호를 입력하세요">
					<span id="memberPwMsg" class="msg"></span>
				</td>
			</tr>
			<tr>
				<td>새로운 비밀번호 확인</td>
				<td>
					<input type="password" name="memberPwCk" id="memberPwCk"placeholder="비밀번호를 한번 더 입력하세요">
					<span id="memberPwCkMsg" class="msg"></span>
				</td>
			</tr>
		</table>
		<button type="submit" id="modifyBtn">수정</button>
	</form>
	
	<div>
	<jsp:include page="/WEB-INF/inc/copyright.jsp"></jsp:include>
	</div>
</body>
</html>