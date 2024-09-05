<!DOCTYPE html>
<html>
<head>
    <title>Photo Blog</title>

    <style>
        img{
            width:500px;
            height: auto;
            object-fit:contain;
        }
    </style>
</head>
<body>
<security:authorize access="isAuthenticated()">
    <c:url var="logoutUrl" value="/logout"/>
    <form action="${logoutUrl}" method="post">
        <input type="submit" value="Logout" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form></security:authorize>
<h2>Photo #${ticketId}: <c:out value="${ticket.subject}"/></h2>
<security:authorize access="isAuthenticated()">
    <security:authorize access="hasRole('ADMIN') or
                principal.username=='${ticket.customerName}'">
        [<a href="<c:url value="/PhotoBlog/edit/${ticket.id}" />">Edit</a>]
    </security:authorize>
    <security:authorize access="hasRole('ADMIN')">
        [<a href="<c:url value="/PhotoBlog/delete/${ticket.id}" />">Delete</a>]<br/><br/>
    </security:authorize>
</security:authorize>

<i>Customer Name - <c:out value="${ticket.customerName}"/></i><br/>
Photo created: <fmt:formatDate value="${ticket.createTime}"
                                pattern="EEE, d MMM yyyy HH:mm:ss Z"/><br/>

Photo description updated: <fmt:formatDate value="${ticket.updateTime}"
                                            pattern="EEE, d MMM yyyy HH:mm:ss Z"/><br/><br/>
<c:if test="${!empty ticket.attachments}">
    Attachments:<c:out value="${attachment.name}"/><br/>
    <c:forEach items="${ticket.attachments}" var="attachment" varStatus="status">
        <a href="<c:url value="/PhotoBlog/${ticketId}/attachment/${attachment.id}" />">
            <img src="<c:url value="/PhotoBlog/${ticketId}/attachment/${attachment.id}" />"/></a><br/>
        photo uploadtime: <fmt:formatDate value="${attachment.createTime}"
                                          pattern="EEE, d MMM yyyy HH:mm:ss Z"/><br/>
        <security:authorize access="isAuthenticated()">
            <security:authorize access="hasRole('ADMIN')">
                [<a href="<c:url value="/PhotoBlog/${ticketId}/delete/${attachment.id}" />">Delete</a>]<br/>
            </security:authorize>
        </security:authorize>
    </c:forEach><br/><br/>
</c:if>
Description:<br/>
<c:out value="${ticket.body}"/><br/><br/>

<c:url var="cUrl" value="${ticketId}/comment/new/"/>
<form:form method="POST" modelAttribute="cForm" action="${cUrl}" cssStyle="width: 50%">

    <form:label path="comment">Comment:</form:label><br/>
    <security:authorize access="isAuthenticated()">
        <form:textarea path="comment" rows="5" cols="30"/><br/><br/>
        <input type="submit" value="Post"/>
    </security:authorize>




</form:form>

<c:choose>
    <c:when test="${empty comments}">No one has left a Comment yet, come and leave a Comment<br/></c:when>
    <c:otherwise>
        <c:forEach items="${comments}" var="comment">
            <i><a href="<c:url value="/user_profile/${comment.username}" />">${comment.username}</a></i> commented on
            <fmt:formatDate value="${comment.createTime}"  pattern="EEE, d MMM yyyy HH:mm:ss Z"/>:<br/>
            <c:out value="${comment.content}"/>
            <security:authorize access="isAuthenticated()">
                <security:authorize access="hasRole('ADMIN')">
                    [<a href="<c:url value="/PhotoBlog/comment/delete/${ticket.id}/${comment.id}" />">Delete</a>]
                </security:authorize>
            </security:authorize>
            <br/><br/>
        </c:forEach>
    </c:otherwise>
</c:choose>


<a href="<c:url value="/PhotoBlog" />">Return to list photos</a>
<c:url var="login" value="/login"/>
</body>
</html>