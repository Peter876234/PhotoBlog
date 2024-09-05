<!DOCTYPE html>
<html>
<head><title>Photo Blog</title></head>

<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<H2>All comments history of:${username}</H2>
<c:choose>
    <c:when test="${fn:length(comments) == 0}">
        <i> ${username} has not commented anything yet.</i>
    </c:when>
    <c:otherwise>
            <c:forEach items="${comments}" var="comment">
                ${username} has Commented "${comment.content}" at Created at <fmt:formatDate value="${comment.createTime}"
                                                                                             pattern="EEE, d MMM yyyy HH:mm:ss"/> <br/>
                <a href="<c:url value="/PhotoBlog/view/${comment.ticketId}" />">click here to visit the photo</a><br/>
            </c:forEach>
    </c:otherwise>
</c:choose>
</body>
<br/>
<a href="<c:url value="/PhotoBlog" />">Return to list photos</a><br/>
<a href="<c:url value="/user" />">Return to user list</a>
</html>