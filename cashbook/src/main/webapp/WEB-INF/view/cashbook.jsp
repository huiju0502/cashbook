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
	<!-- 메인메뉴 -->
	<div>
		<jsp:include page="/WEB-INF/inc/mainmenu.jsp"></jsp:include>
	</div>
	
	<h1>${targetYear}년 ${targetMonth+1}월 ${targetDate}일</h1>
	
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
				<th>삭제</th>
			</tr>
			<c:forEach var="c" items="${list}">
				<form action="${pageContext.request.contextPath}/removeCashbook?targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${targetDate}&cashbookNo=${c.cashbookNo}" method="post">
					<tr>
						<td>${c.category}</td>
						<td>${c.price}</td>
						<td>${c.memo}</td>
						<td>${c.createdate}</td>
						<td><button type="submit">삭제</button></td>
					</tr>
				</form>
			</c:forEach>
		</table>
		
	<div>
	<jsp:include page="/WEB-INF/inc/copyright.jsp"></jsp:include>
	</div>
</body>
</html>