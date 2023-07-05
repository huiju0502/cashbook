<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	${word}
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
	
	<!-- 페이지 네비게이션 -->
	<c:if test="${minPage > 1}">
		<a href="${pageContext.request.contextPath}/cashbookListByTag?word=${word}&currentPage=${minPage-pagePerPage}">이전</a>
	</c:if>
	<c:forEach var="i" begin="${minPage}" end="${maxPage}" step="1">
		<c:if test="${i == currentPage}">
			<a>${i}</a>
		</c:if>
		<c:if test="${i != currentPage}">
			<a href="${pageContext.request.contextPath}/cashbookListByTag?word=${word}&currentPage=${i}">${i}</a>
		</c:if>
	</c:forEach>
	<c:if test="${maxPage != lastPage}">
		<a href="${pageContext.request.contextPath}/cashbookListByTag?word=${word}&currentPage=${minPage+pagePerPage}">다음</a>
	</c:if>
</body>
</html>