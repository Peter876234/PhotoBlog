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
<h2> ${profile.username}'s Profile:<br/> </h2>

Description:<br/>
<c:choose>
    <c:when test="${empty profile.description}">
        This guy is lazy, dont have any description.
    </c:when>
    <c:otherwise>
        ${profile.description}
    </c:otherwise>
</c:choose>

<security:authorize access="isAuthenticated()">
    <security:authorize access="hasRole('ADMIN') or principal.username=='${profile.username}'">
        [<a href="<c:url value="/user_profile/edit/${profile.username}" />">Edit</a>]
    </security:authorize>
</security:authorize>
<br/>
<br/>
<c:choose>
    <c:when test="${fn:length(ticketDatabase) == 0}">
        <i>This user has not creare any Photo Blog yet.</i>
    </c:when>
    <c:otherwise>
        <c:forEach items="${ticketDatabase}" var="entry">
            Photo Blog ${entry.id}:
            <a href="<c:url value="/PhotoBlog/view/${entry.id}" />">
                <c:out value="${entry.subject}"/></a>
            (customer:<a href="<c:url value="/user_profile/${entry.customerName}"/>"> <c:out value="${entry.customerName}"/></a>)
            <security:authorize access="isAuthenticated()">
                <security:authorize access="hasRole('ADMIN') or
                principal.username=='${entry.customerName}'">
                    <br/>photo uploadtime: ${profile.createTime}
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


</body>
<br/><br/>
<a href="<c:url value="/PhotoBlog" />">Return to list photos</a>
</html>