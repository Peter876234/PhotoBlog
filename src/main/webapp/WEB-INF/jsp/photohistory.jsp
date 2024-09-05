<!DOCTYPE html>
<html>
<head><title>Photo Blog</title></head>

<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<a href="<c:url value="/PhotoBlog" />">Return to list photos</a><br/>
<a href="<c:url value="/user" />">Return to user list</a>


<H2>All photo history of: ${username}</H2>

<c:choose>
    <c:when test="${fn:length(ticketDatabase) == 0}">
        <i>This user has not uploaded anything yet.</i>
    </c:when>
    <c:otherwise>
        <c:forEach items="${ticketDatabase}" var="entry">
            PhotoBlog ${entry.id}:
            <a href="<c:url value="/PhotoBlog/view/${entry.id}" />">
                <c:out value="${entry.subject}"/></a>
            <br/>
            Created at <fmt:formatDate value="${entry.createTime}"
                                       pattern="EEE, d MMM yyyy HH:mm:ss"/><br/>
            <security:authorize access="isAuthenticated()">
                <security:authorize access="hasRole('ADMIN') or
                principal.username=='${entry.customerName}'">
                    [<a href="<c:url value="/PhotoBlog/edit/${entry.id}" />">Edit</a>]
                </security:authorize>
                <security:authorize access="hasRole('ADMIN')">
                    [<a href="<c:url value="/PhotoBlog/delete/${entry.id}" />">Delete</a>]<br/><br/>
                </security:authorize>
            </security:authorize>
        </c:forEach>
    </c:otherwise>
</c:choose>
</body></html>