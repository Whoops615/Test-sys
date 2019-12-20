<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>


	<form action="Controller" method="post">
		<input type="hidden" name="command" value="teacher_command">
		<input type="hidden" name="action" value="create_test">
		<input type="submit" value="create test">	
	</form>
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="teacher_command">
		<input type="hidden" name="action" value="create_question">
		<input type="submit" value="create question">	
	</form>



</body>
</html>