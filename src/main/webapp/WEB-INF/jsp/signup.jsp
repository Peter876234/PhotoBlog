<!DOCTYPE html>
<html>
<head><title>Fill in the form for sign up Photo Blog</title></head>
<body>

<h2>Sign up</h2>
<form:form method="POST" modelAttribute="ticketUser">

    <form:label path="username">Username</form:label><br/>
    <form:input type="text" path="username"/><br/><br/>

    <form:label path="password">Password</form:label><br/>
    <form:input type="text" path="password"/><br/><br/>

    <form:label path="email">Email</form:label><br/>
    <form:input type="email" path="email"/><br/><br/>

    <form:label path="phonenumber">Phone Number</form:label><br/>
    <form:input type="tel" path="phonenumber" pattern="[0-9]{8}"/><br/><br/>

    <br/><br/>
    <input type="submit" value="Sign up"/>
</form:form>
<a href="<c:url value="/login" />">Return to login page</a>
</body>
</html>