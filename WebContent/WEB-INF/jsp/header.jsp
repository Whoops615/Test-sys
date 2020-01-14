<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />

<fmt:message bundle="${loc}" key="local.header1" var="header1" />
<fmt:message bundle="${loc}" key="local.header2" var="header2" />


</head>
<body>

		<em><c:out value="${sessionScope.user.name}" /> </em>
		<em><c:out	value="${sessionScope.user.surname}" /></em> 
		<span> <c:out value="${header1}" /></span>
		<h4>
			<span><c:out value="${header2}" /></span>
			<span><c:out value="${sessionScope.user.role}" /></span>
		</h4>

</body>
</html>