<!DOCTYPE html>
<html>
<head>
  <title>Photo Blog</title>
</head>
<body>
<h2>Error page</h2>
<c:choose>
  <c:when test="${empty message}">
    <p>Something went wrong.</p>
  </c:when>
  <c:otherwise>
    <p>${message}</p>
  </c:otherwise>
</c:choose>
<a href="javascript:history.back(-1)">Back to previous page</a>
</body>
</html>
