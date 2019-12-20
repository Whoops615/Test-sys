<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />

<fmt:message bundle="${loc}" key="local.menuright1" var="menuright1" />
<fmt:message bundle="${loc}" key="local.menuright2" var="menuright2" />
<fmt:message bundle="${loc}" key="local.menuright3" var="menuright3" />
<fmt:message bundle="${loc}" key="local.menuright4" var="menuright4" />
<fmt:message bundle="${loc}" key="local.menuright5" var="menuright5" />

</head>
<body>

	<h4><c:out value="${menuright1}" /></h4>

			<form action="authorization"><button type="submit"><c:out value="${menuright3}" /></button></form>
			<form action="registration"><button type="submit"><c:out value="${menuright4}" /></button></form>

			<form action="Controller" method="post">
				<input type="hidden" name="command" value="logout">
				<button type="submit"><c:out value="${menuright5}" /></button>
			</form>

			<h5><c:out value="${menuright2}" /></h5>
			
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="localization">
				<input	type="hidden" name="local" value="en">
				<input type="submit" value="EN"><br>
			</form>

			<form action="Controller" method="post">
				<input type="hidden" name="command" value="localization">
				<input	type="hidden" name="local" value="ru">
				<input type="submit" value="RU"><br>
			</form>


</body>
</html>