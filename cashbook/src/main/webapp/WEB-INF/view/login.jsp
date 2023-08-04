<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<h1>로그인</h1>
	<form action="${pageContext.request.contextPath}/login" method="post">
		<table border="1">
			<tr>
				<td>아이디</td>
				<td><input type="text" name="memberId" value="${loginId}"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="memberPw"></td>
			</tr>
		</table>
		<input type="checkbox" name="idSave" value="y">ID저장
		<button type="submit">로그인</button>
		<a href="${pageContext.request.contextPath}/addMember">회원가입</a>
	</form>
	
	<div>
	<jsp:include page="/WEB-INF/inc/copyright.jsp"></jsp:include>
	</div>
</body>
</html>