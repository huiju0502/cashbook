<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원 상세정보</h1>
	<table border="1">
		<tr>
			<td>id</td>
			<td>${memberOne.memberId}</td>
		<tr>
		<tr>
			<td>pw</td>
			<td>${memberOne.memberPw}</td>
		<tr>
		<tr>
			<td>updatadate</td>
			<td>${memberOne.updatedate}</td>
		<tr>
		<tr>
			<td>createdate</td>
			<td>${memberOne.createdate}</td>
		<tr>
	</table>
	<a href="${pageContext.request.contextPath}/modifyMember">회원정보수정</a>
	<a href="${pageContext.request.contextPath}/removeMember">회원탈퇴</a>
</body>
</html>