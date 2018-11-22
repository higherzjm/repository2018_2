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
        function serverLaunch() {
            console.info("netty 服务端启动")
            $.ajax( {
                type : "GET",//(默认: "GET") 请求方式 ("POST" 或 "GET")，
                url : "nettyController/serverLaunch.do",
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
        function clientConnect() {
            console.info("netty  客户端连接")
            $.ajax( {
                type : "GET",//(默认: "GET") 请求方式 ("POST" 或 "GET")，
                url : "nettyController/clientConnect.do",
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


        function sendMsg() {
            var senValue=$("#senValue").val();
            console.info("netty  发送和接收信息;senValue:"+senValue)
            $.ajax( {
                type : "GET",//(默认: "GET") 请求方式 ("POST" 或 "GET")，
                url : "nettyController/senMsg.do?msg="+senValue,
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
<input type="button" value="服务端启动" onclick="serverLaunch()"><br><br>
 <input type="button" value="客户端连接" onclick="clientConnect()"><br><br>
 消息发生：<input type="text" id="senValue"/><br><br>
 <input type="button" value="消息发生" onclick="sendMsg()">
</body>
</html>
