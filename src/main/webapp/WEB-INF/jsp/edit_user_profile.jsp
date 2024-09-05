<!DOCTYPE html>
<html>
<head>
    <title>Photo Blog</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<h2>Edit description #${ticketuser.username}</h2>
<form:form method="POST" modelAttribute="ticketuserForm">
    <form:label path="description">Description</form:label><br/>
    <form:textarea path="description" rows="5" cols="30" /><br/><br/>
    <input type="submit" value="Save Change"/>
</form:form>
<a href="<c:url value="/PhotoBlog" />">Return to list tickets</a>
</body>
</html>

