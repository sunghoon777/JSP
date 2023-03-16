<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>JSP 파일 다운로드</title>
</head>
<body>

<c:forEach var="file" items="${files}">
    <a href="${'/downloadAction?file='}${file.fileName}">${file.fileRealName}</a>
    <br>
    <p>${'다운로드 횟수 : '}${file.downloadCount}</p>
    <br>
</c:forEach>
</body>
</html>