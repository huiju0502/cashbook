<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원정보수정</h1>
	<form action="${pageContext.request.contextPath}/modifyMember" method="post">
		<table border="1">
			<tr>
				<td>pw</td>
				<td><input type="password" name="memberPw"></td>
			</tr>
		</table>
		<button type="submit">수정</button>
	</form>

</body>
</html>