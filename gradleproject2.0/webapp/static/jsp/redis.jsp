<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"+request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <script src="style/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
    <title>Title</title>
    <script type="text/javascript">
        function setingupalltypemsg() {
            var  shcoolName=document.getElementById("schoolName").value;;
            var  majorName=document.getElementById("majorName").value;;
            console.info("shcoolName:"+shcoolName)
            console.info("majorName:"+majorName)
            console.info("redis  发送消息")
            $.ajax( {
                type : "POST",//(默认: "GET") 请求方式 ("POST" 或 "GET")，
                url : "send_redis_merssagecontroller/setingupalltypemsg.do?majorName="+majorName,
                data : {shcoolName:shcoolName},
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


        function getalltypemsg() {
            console.info("redis  获取消息")
            $.ajax( {
                type : "GET",//(默认: "GET") 请求方式 ("POST" 或 "GET")，
                url : "send_redis_merssagecontroller/getalltypemsg.do",
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

        function sendmsg() {
            console.info("spring redis  消息发送监听系统")
            $.ajax( {
                type : "POST",//(默认: "GET") 请求方式 ("POST" 或 "GET")，
                url : "send_redis_merssagecontroller/sendmsg.do",
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
<body style="text-align: left;padding: 10px 0 0 10px">
学校名称：<input type="text"  id="schoolName"/><br/>
专业名称：<input type="text"  id="majorName"/><br/><<br/>
<input type="button" value="redis设置各种类型的数据格式" onclick="setingupalltypemsg()"/>&nbsp;
<br/><br/>
<input type="button" value="redis获取各种类型的数据格式" onclick="getalltypemsg()"/>
<br/><br/>
<input type="button" value="spring redis 消息发送监听系统" onclick="sendmsg()"/>
</body>
</html>
