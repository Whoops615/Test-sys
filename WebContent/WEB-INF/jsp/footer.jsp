<%@ page language="java" contentType="text/html; charset=ISO-utf-8"
    pageEncoding="utf-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<style>
#footer {background-color: #ded9d9;	border: 1px solid black; margin-left: 5px; margin-top: 2px;}
</style>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />

<fmt:message bundle="${loc}" key="local.footer1" var="footer1" />
</head>
<body>

<p><c:out value="${footer1}" /><em>Whoops</em></p>

</body>
</html>