<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="Controller" method="post">
		<input type="hidden" name="command" value="question_setting">
		<input type="hidden" name="action" value="create_question">
		
		
		<h4>Select the number of answers</h4>
		2<input type="radio" name="numberResponses" value="2"><br>
		3<input type="radio" name="numberResponses" value="3"><br>
		4<input type="radio" name="numberResponses" value="4"><br>
		
		
		
		
		<input type="reset" value="reset">
		<input type="submit" value="proceed">
	
	</form>

</body>
</html>