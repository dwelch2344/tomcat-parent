<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tags:template>
	<jsp:attribute name="head">  
		<script type="text/javascript">
			// inline JavaScript here
		</script>
  	</jsp:attribute>
	<jsp:body>
		<h1>Login Required</h1>
		<form action="doLogin" method="POST">
			<label for="username">User Name:</label>
			<input id="username" name="user" type="text" />
			<label for="password">Password:</label>
			<input id="password" name="password" type="password" />
			<input type="submit" value="Log In" />
		</form>
	</jsp:body>
</tags:template>