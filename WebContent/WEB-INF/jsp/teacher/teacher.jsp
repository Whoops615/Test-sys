<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
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
#content {width: 54%; height: 700px; background: #d3b7e6;}
#menuright {width: 20%; height: 700px; background: #ADD0D9;}
#clear {clear: both;}

#header {background-color: #d9ecd4;	height: 120px; border: 1px solid black; margin-left: 5px;}
</style>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />

<fmt:message bundle="${loc}" key="local.header1" var="header1" />
<fmt:message bundle="${loc}" key="local.header2" var="header2" />

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
	
	
	<div id="menuleft">		
		<c:import url="/WEB-INF/jsp/teacher/menu.jsp"/>	
	</div>
		
	<div id="content">
	
	<h4>
		<c:if test="${not empty param.message}">
		<c:out value="${param.message}"></c:out>
		</c:if>
	</h4>	
				
		<c:choose>
		
			<c:when test="${param.action eq 'create_test'}">
      			<c:import  url="/WEB-INF/jsp/test/createTest.jsp"/>
    		</c:when>
    		
    		
    		<c:when test="${param.action eq 'create_question'}">
    			<c:import url="/WEB-INF/jsp/test/createQuestion.jsp"/>
    		</c:when>
    
   			<c:otherwise>
       
    		</c:otherwise>
		
		</c:choose>
		 					
	</div>
		
	<div id="menuright">		
		<c:import url="/WEB-INF/jsp/menuRight.jsp"/>			
	</div>
		
	</div>
	
	<div id="clear"></div>
	<div id="footer">
		<c:import url="/WEB-INF/jsp/footer.jsp"/>
	</div>

</body>
</html>