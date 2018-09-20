<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"+request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>http</title>
</head>
<body style="text-align: left;padding: 10px 0 0 10px">
<a href="httptest/get.do" >http原始-get</a>&nbsp;&nbsp;&nbsp;<a href="httptest/post.do" >http原始-post</a>&nbsp;<br>
<br/><br/>

</body>
</html>
