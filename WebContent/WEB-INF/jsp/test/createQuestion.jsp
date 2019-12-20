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
		<input type="hidden" name="command" value="save_question">
		<p>Enter test name:</p>
		<input type="text"  name="title" value=""><br>
		
		<p>Enter the question text:</p>
		<textarea name="text" rows="8" cols="80"></textarea><br>
		
		<p>Enter the answer text:</p>
		<input type="text" name="answer1" size="50" value=""><br>
		True<input type="radio" name="result1" value="true">
		False<input type="radio" name="result1" value="false">
		Include answer<input type="checkbox" name="include1" value="yes"><br>
		
		<p>Enter the answer text:</p>
		<input type="text" name="answer2" size="50" value=""><br>
		True<input type="radio" name="result2" value="true">
		False<input type="radio" name="result2" value="false">
		Include answer<input type="checkbox" name="include2" value="yes"><br>
		
		<p>Enter the answer text:</p>
		<input type="text" name="answer3" size="50" value=""><br>
		True<input type="radio" name="result3" value="true">
		False<input type="radio" name="result3" value="false">
		Include answer<input type="checkbox" name="include3" value="yes"><br>
		
		<p>Enter the answer text:</p>
		<input type="text" name="answer4" size="50" value=""><br>
		True<input type="radio" name="result4" value="true">
		False<input type="radio" name="result4" value="false">
		Include answer<input type="checkbox" name="include4" value="yes"><br>
		
		<input type="reset" value="reset"><br>
		<input type="submit" value="save question">
	</form>
	
	
</body>
</html>