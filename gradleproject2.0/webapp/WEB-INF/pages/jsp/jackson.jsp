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
        function jacksonreadalldatas() {
            $.ajax( {
                type : "POST",//(默认: "GET") 请求方式 ("POST" 或 "GET")，
                url : "jacksonController/test1.do",
                data : "",
                async: false,
                dataType : "text",
                success : function(data) {
                    alert("所有数据:"+data)

                } ,
                error:function(data){
                    console.log('error:'+data);
                }
            });
        }


        function jacksonlistreadlistdata() {
            $.ajax( {
                type : "GET",//(默认: "GET") 请求方式 ("POST" 或 "GET")，
                url : "jacksonController/test1.do",
                data : "",
                async: false,
                dataType : "json",
                success : function(data) {
                    alert("list data:"+data.list)

                } ,
                error:function(data){
                    console.log('error:'+data);
                }
            });
        }

        function jacksonreadobjctattrdata() {
            $.ajax( {
                type : "POST",//(默认: "GET") 请求方式 ("POST" 或 "GET")，
                url : "jacksonController/test1.do",
                data : "",
                async: false,
                dataType : "json",
                success : function(data) {
                    alert("link phone:"+data.link.phone)

                } ,
                error:function(data){
                    console.log('error:'+data);
                }
            });
        }
    </script>
</head>
<body style="text-align: left;padding: 10px 0 0 10px">
<input type="button" value="jackson读取所有数据" onclick="jacksonreadalldatas()"/>&nbsp;
<br/><br/>
<input type="button" value="jackson读取指定list的数据" onclick="jacksonlistreadlistdata()"/>
<br/><br/>
<input type="button" value="jackson读取指定对象的属性的数据" onclick="jacksonreadobjctattrdata()"/>
</body>
</html>
