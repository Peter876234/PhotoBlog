<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
  <title>Photo Blog</title>
</head>
<body>
<security:authorize access="isAuthenticated()">
  <security:authentication var="username" property="principal.username"/>
  <c:url var="logoutUrl" value="/logout"/>
  <form action="${logoutUrl}" method="post">
    <input type="submit" value="Logout" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  </form>
</security:authorize>
<h2>Photos Blog</h2>
<security:authorize access="isAuthenticated()">
  Name:${username}<br/>
  <security:authorize access="hasRole('ADMIN')">
    <a href="<c:url value="/user" />">Manage User Accounts</a><br /><br />
  </security:authorize>
  <a href="<c:url value="/user_profile/${username}"/>">Manage Your Profile</a><br /><br />

  <a href="<c:url value="/PhotoBlog/create" />">Create a Photo Blog</a><br/><br/>
</security:authorize>

<c:choose>
  <c:when test="${fn:length(ticketDatabase) == 0}">
    <i>There are not any photos in the system.</i>
  </c:when>
  <c:otherwise>
    <c:forEach items="${ticketDatabase}" var="entry">
      PhotoBlog ${entry.id}:
      <a href="<c:url value="/PhotoBlog/view/${entry.id}" />">
        <c:out value="${entry.subject}"/></a>
      (customer:<a href="<c:url value="/user_profile/${entry.customerName}"/>"> <c:out value="${entry.customerName}"/></a>)
      <security:authorize access="isAuthenticated()">
        <security:authorize access="hasRole('ADMIN') or
                principal.username=='${entry.customerName}'">
          [<a href="<c:url value="/PhotoBlog/edit/${entry.id}" />">Edit</a>]
        </security:authorize>
        <security:authorize access="hasRole('ADMIN')">
          [<a href="<c:url value="/PhotoBlog/delete/${entry.id}" />">Delete</a>]
        </security:authorize>
      </security:authorize>
      <br/>
    </c:forEach>
  </c:otherwise>
</c:choose>
<br/>
<security:authorize access="!isAuthenticated()">
  <a href="<c:url value="/login" />">Return to the login page</a>

</security:authorize>
</body>
</html>
