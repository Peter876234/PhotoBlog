<!DOCTYPE html>
<html>
<head><title>Photo Blog</title></head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>


<h2>Edit ${username.username} information</h2>
<form:form method="POST" modelAttribute="ticketUser">

    <form:label path="username">Username:</form:label><br/>
    ${username.username}<br/><br/>

    <form:label path="password">Password:</form:label><br/>
    <form:input type="text" path="password"/><br/><br/>

    <form:label path="email">Email:</form:label><br/>
    <form:input type="email" path="email"/><br/><br/>

    <form:label path="phoneNumber">Phone Number:</form:label><br/>
    <form:input type="tel" pattern="[0-9]{8}" path="phoneNumber"/><br/><br/>

    <form:label path="description">Description:</form:label><br/>
    <form:textarea path="description" rows="5" cols="30" /><br/><br/>

    <form:label path="roles">Roles:</form:label><br/>
    <form:checkbox path="roles" value="ROLE_USER"/>ROLE_USER
    <form:checkbox path="roles" value="ROLE_ADMIN"/>ROLE_ADMIN

    <br/><br/>
    <input type="submit" value="Submit"/>
</form:form>
</body>
<br/>
<a href="<c:url value="/PhotoBlog" />">Return to list photos</a><br/>
<a href="<c:url value="/user" />">Return to list users</a>

</html>