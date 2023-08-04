<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><!-- jstl에서 substring 호출 -->
<!-- jsp컴파일시 자바코드로 변환되는 c:... 커스텀태그 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 메인메뉴 -->
	<div>
		<jsp:include page="/WEB-INF/inc/mainmenu.jsp"></jsp:include>
	</div>
	
	<!-- 변수값 or 반환값 : EL사용 $ 표현식-->
	<!-- 
		속성값대신 EL사용
		ex)
		request.getAttribute("targetYear") -- requestScope.targetYear 
		(requestScope는 생략가능)
		형변환연산이 필요없다(EL이 자동으로 처리)
	-->
	
	<!-- 자바코드(제어문) : JSTL사용 -->
	<h1>
		<a href="${pageContext.request.contextPath}/calendar?targetYear=${targetYear}&targetMonth=${targetMonth-1}">이전달</a>
		${targetYear}년 ${targetMonth+1}월
		<a href="${pageContext.request.contextPath}/calendar?targetYear=${targetYear}&targetMonth=${targetMonth+1}">다음달</a>
	</h1>
	<div>
		<h2>이달의 해시태그</h2>
		<div>
			<c:forEach var="m" items="${htList}">
			<a href="${pageContext.request.contextPath}/cashbookListByTag?word=${m.word}">${m.word}(${m.cnt})</a>
			</c:forEach>
		</div>
	
	</div>
	<table border="1" width="80%">
		<tr>
			<th>일요일</th>
			<th>월요일</th>
			<th>화요일</th>
			<th>수요일</th>
			<th>목요일</th>
			<th>금요일</th>
			<th>토요일</th>
		</tr>
		<tr>
		 <c:forEach var="i" begin="0" end="${totalCell - 1}" step="1">
		 	<c:set var="d" value="${i-beginBlank+1}"></c:set>
					<c:if test="${i!=0 && i%7==0}">
						</tr><tr>
					</c:if>
					
					<c:if test="${d < 1 || d > lastDate}">
						<td></td>
					</c:if>
					
					<c:if test="${!(d < 1 || d > lastDate)}">
						<td>
							<div><a href="${pageContext.request.contextPath}/cashbook?targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${d}">${d}</a></div>
							<c:forEach var="c" items="${list}">
								<c:if test="${d == fn:substring(c.cashbookDate,8,10)}">
									<div>
										<c:if test="${c.category == '수입'}">
											<span>+${c.price}</span>
										</c:if>
										<c:if test="${c.category == '지출'}">
											<span style="color:red;">-${c.price}</span>
										</c:if>
									</div>
								</c:if>
							</c:forEach>
						</td>
					</c:if>
		</c:forEach> 
		</tr>
	</table>
	
	<div>
	<jsp:include page="/WEB-INF/inc/copyright.jsp"></jsp:include>
	</div>
</body>
</html>