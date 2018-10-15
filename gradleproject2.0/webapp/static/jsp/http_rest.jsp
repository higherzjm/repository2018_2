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
<a href="restTemplateAction/RestTem.do?method=get" >http_rest-get</a>&nbsp;&nbsp;&nbsp;<a href="restTemplateAction/RestTem.do?method=post" >http_rest-post</a>&nbsp;<br>
<br/><br/>

</body>
</html>
