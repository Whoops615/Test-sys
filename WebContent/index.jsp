<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
#menuleft, #content, #menuright {
	padding: 5px;
	margin-left: 5px;
	margin-top: 2px;
	float: left;
	border: 1px solid black;
}

#menuleft {width: 20%;	height: 700px; background: #EBE0C5;}
#content {width: 55%; height: 700px; background: #d3b7e6;}
#menuright {width: 20%; height: 700px; background: #ADD0D9;}
#clear {clear: both;}
#footer {background-color: #ded9d9;	border: 1px solid black; margin-left: 5px; margin-top: 2px;}
#header {background-color: #d9ecd4;	height: 120px; border: 1px solid black; margin-left: 5px;}
</style>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />

<fmt:message bundle="${loc}" key="local.header1" var="header1" />
<fmt:message bundle="${loc}" key="local.header2" var="header2" />
<fmt:message bundle="${loc}" key="local.menuright1" var="menuright1" />
<fmt:message bundle="${loc}" key="local.menuright2" var="menuright2" />
<fmt:message bundle="${loc}" key="local.menuright3" var="menuright3" />
<fmt:message bundle="${loc}" key="local.menuright4" var="menuright4" />
<fmt:message bundle="${loc}" key="local.menuright5" var="menuright5" />
<fmt:message bundle="${loc}" key="local.footer1" var="footer1" />

</head>
<body>


	<div id="header">

		<em><c:out value="${sessionScope.user.name}" /> </em>
		<em><c:out	value="${sessionScope.user.surname}" /></em> 
		<span> <c:out value="${header1}" /></span>
		<h4>
			<span><c:out value="${header2}" /></span>
			<span><c:out value="${sessionScope.user.role}" /></span>
		</h4>

	</div>

	<div class="container">
		<div id="menuleft"></div>
		<div id="content">	
		
		 					
		</div>
		<div id="menuright">
		
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

		</div>
		
	</div>
	
	<div id="clear"></div>

	<div id="footer">
		<p><c:out value="${footer1}" /><em>Whoops</em></p>
	</div>

</body>
</html>