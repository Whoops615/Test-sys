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
		<input type="hidden" name="command" value="go_to_create_test_page">
		<input type="submit" value="create test">	
	</form>
	
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="go_to_create_question_page">
		<input type="submit" value="create question">	
	</form>
	
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="go_to_appointment_test_page">
		<input type="submit" value="appointment test">	
	</form>
		

</body>
</html>