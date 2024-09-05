<!DOCTYPE html>
<html>
<head><title>Photo Blog</title></head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
  <input type="submit" value="Log out"/>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<h2>Create a User</h2>


<form:form method="POST" modelAttribute="ticketUser">

  <form:label path="username">Username</form:label><br/>
  <form:input type="text" path="username"/><br/><br/>

  <form:label path="password">Password</form:label><br/>
  <form:input type="text" path="password"/><br/><br/>

  <form:label path="email">Email</form:label><br/>
  <form:input type="email" path="email"/><br/><br/>

  <form:label path="phoneNumber">Phone Number</form:label><br/>
  <form:input type="tel" pattern="[0-9]{8}" path="phoneNumber"/><br/><br/>

  <security:authorize access="isAuthenticated()">
    <security:authorize access="hasRole('ADMIN')">
      <form:label path="roles">Roles:</form:label><br/>
      <form:checkbox path="roles" value="ROLE_USER"/>ROLE_USER
      <form:checkbox path="roles" value="ROLE_ADMIN"/>ROLE_ADMIN
    </security:authorize>
  </security:authorize>

  <br/><br/>
  <input type="submit" value="Add User"/>
</form:form>
</body>
<br/>
<a href="<c:url value="/user" />">Return to user list</a>
</html>