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




</head>
<body>


	<div id="header">
		<c:import url="/WEB-INF/jsp/header.jsp"/>
	</div>

	<div class="container">
		<div id="menuleft"></div>
		<div id="content">
		
		
		
			<c:if test="${not empty param.message}">
				<c:out value="${param.message}"></c:out>
			</c:if>
			
			
			
			<c:if test="${not empty message}">
				<c:out value="${message}"></c:out>
			</c:if>
			
			
		
		
		
		
			
		
		 					
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