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
		<input type="hidden" name="command" value="create_test">
		Title: <input type="text" name="title" value="">
		Time: <input type="time" name="time" >
		<input type="submit" value="save test">
	
	</form>


</body>
</html>