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

</body>
</html>