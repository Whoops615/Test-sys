<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>

<h4>
<c:if test="${not empty param.errorMessage}">
<c:out value="${param.errorMessage}"></c:out>
</c:if>
</h4>


	<form action="Controller" method="post">
		<input type="hidden" name="command" value="registration">
		Login: <input type="text" name="login" value=""><br>
		Password: <input type="password" name="password" value=""><br>
		Email: <input type="email" name="email" value=""><br>
		Role: <input type="text" name="role" value=""><br>
		Name: <input type="text" name="name" value=""><br>
		Surname <input type="text" name="surname" value=""><br>
		<input type="submit" value="sing in">
	
	</form>




</body>
</html>