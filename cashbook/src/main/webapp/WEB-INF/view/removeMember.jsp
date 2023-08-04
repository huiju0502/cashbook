<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>

</script>
</head>
<body>
	<!-- 메인메뉴 -->
	<div>
		<jsp:include page="/WEB-INF/inc/mainmenu.jsp"></jsp:include>
	</div>
	
	<h1>회원탈퇴</h1>
	<form id ="removeForm" action="${pageContext.request.contextPath}/removeMember" method="post">
		<table border="1">
			<tr>
				<td>비밀번호</td>
				<td>
					<input type="password" name="memberPw" id="memberPw">
					<span id="memberPwMsg" class="msg"></span>
				</td>
			</tr>
		</table>
		<button type="submit" id="removeBtn">탈퇴</button>
	</form>
	
	<div>
	<jsp:include page="/WEB-INF/inc/copyright.jsp"></jsp:include>
	</div>
</body>
</html>