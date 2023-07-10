<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cashbook.jsp</title>
</head>
<body>
	<h1>${targetYear}년 ${targetMonth+1}월 ${targetDate}일</h1>
	
	<a href="${pageContext.request.contextPath}/memberOne">마이페이지</a>
	<a href="${pageContext.request.contextPath}/calendar">홈</a>
	<a href="${pageContext.request.contextPath}/logout">로그아웃</a>
	<!-- 추가폼 -->
		<form action="${pageContext.request.contextPath}/addCashbook?targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${targetDate}" method="post">
			<table border="1">
				<tr>
					<td>분류</td>
					<td>
						<input type="radio" name="category" value="수입">수입
						<input type="radio" name="category" value="지출">지출
					</td>
				</tr>
				<tr>
					<td>금액</td>
					<td><input type="number" name="price"></td>
				</tr>
				<tr>
					<td>메모</td>
					<td><input type="text" name="memo"></td>
				</tr>
			</table>
			<button type="submit">추가</button>
		</form>
	<!-- 날짜별 리스트 출력 -->
	<table border="1">
		<tr>
			<th>분류</th>
			<th>금액</th>
			<th>메모</th>
			<th>작성날짜</th>
			<th>수정날짜</th>
		</tr>
		<c:forEach var="c" items="${list}">
		<tr>
			<td>${c.category}</td>
			<td>${c.price}</td>
			<td>${c.memo}</td>
			<td>${c.createdate}</td>
			<td>${c.updatedate}</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>