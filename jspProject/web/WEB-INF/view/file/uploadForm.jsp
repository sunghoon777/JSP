<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>JSP 파일 업로드</title>
</head>
<body>
<form action="/uploadAction" method="post" enctype="multipart/form-data">
    파일 : <input type="file" name="file1">
    파일 : <input type="file" name="file2">
    파일 : <input type="file" name="file3">
    <br>
    <input type="submit" value="업로드"/>
</form>
</body>
</html>
