<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<jsp:directive.attribute name="head" required="false" fragment="true"/>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title> tomcat-web </title>

		<%-- Set the base URL based on context --%>
		<c:url value="/" var="baseUrl" />
		<c:choose>
			<c:when test="${fn:length(baseUrl) <= 2}">
				<%-- If we're deployed as ROOT, it should be empty --%>
				<c:set var="baseUrl" value=""/>
			</c:when>
			<c:otherwise>
				<%-- If we're not deployed as ROOT, trim the trailing slash --%>
				<c:set value="${fn:length(baseUrl)}" var="baseUrlLen" />
				<c:set var="baseUrl" value="${fn:substring(baseUrl, 0, baseUrlLen - 1)}"/>
			</c:otherwise>
		</c:choose>
		
    	<link rel="stylesheet" type="text/css" media="all" href="${baseUrl}/resources/bootstrap/css/bootstrap.css"/>
    	<link rel="stylesheet" type="text/css" media="all" href="${baseUrl}/resources/bootstrap/css/bootstrap-responsive.css"/>
		<link rel="stylesheet" type="text/css" media="all" href="${baseUrl}/resources/style.css"/>
		<script type="text/javascript" src="${baseUrl}/resources/project.js" ></script>
		<jsp:invoke fragment="head"/>
	</head>
	<body>
		<jsp:doBody />
	</body>
</html>