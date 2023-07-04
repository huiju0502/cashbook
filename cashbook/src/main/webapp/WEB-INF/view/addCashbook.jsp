<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>addCashbook</title>
</head>
<body>
	<h1>추가</h1>
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
</body>
</html>