<%--
  Created by IntelliJ IDEA.
  User: zjm
  Date: 2018/11/6
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"+request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>netty</title>
    <script src="style/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
        function example1() {
            console.info("netty 服务端启动")
            $.ajax( {
                type : "GET",//(默认: "GET") 请求方式 ("POST" 或 "GET")，
                url : "springbatchcontroller/example1.do",
                data : "",
                async: false,
                dataType : "text",
                success : function(data) {
                    alert(data)

                } ,
                error:function(data){
                    console.log('error:'+data);
                }
            });
        }



    </script>
</head>
<body style="text-align: left">
<input type="button" value="例1" onclick="example1()"><br><br>
</body>
</html>
