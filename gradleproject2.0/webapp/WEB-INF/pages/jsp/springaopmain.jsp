<%--
  Created by IntelliJ IDEA.
  User: zjm
  Date: 2019/1/3
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"+request.getServerName() + ":" + request.getServerPort() + path + "/style/";
    System.out.println("basePath:"+basePath);
    String basePath2 = request.getScheme() + "://"+request.getServerName() + ":" + request.getServerPort() + path + "/";
    System.out.println("basePath2:"+basePath2);
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script  type="text/javascript">
        function test1(value) {
            $.ajax( {
                type : "POST",//(默认: "GET") 请求方式 ("POST" 或 "GET")，
                url : "<%=basePath2%>springaopmaincontroller/test1.do?throwException="+value,
                data : "",
                async: false,
                dataType : "json",
                success : function(data) {
                    alert("success:"+data)
                    alert("size："+data.id+"；names:"+data.name)
                } ,
                error:function(data){
                    alert("error:"+data)
                    alert("size："+data.id+"；names:"+data.name)
                }
            });
        }

        function test2(name) {
            $.ajax( {
                type : "POST",//(默认: "GET") 请求方式 ("POST" 或 "GET")，
                url : "<%=basePath2%>springaopmaincontroller/test2.do",
                data : {name:name,age:30},
                async: false,
                dataType : "json",
                success : function(data) {
                    alert("success-->name:"+data.name+";age:"+data.age)
                } ,
                error:function(data){
                    alert("error-->name:"+data.name+";age:"+data.age)
                }
            });
        }

        function test3() {
            $.ajax( {
                type : "POST",//(默认: "GET") 请求方式 ("POST" 或 "GET")，
                url : "<%=basePath2%>springaopmaincontroller/unPointcut.do",
                data : {name:'张三',age:30},
                async: false,
                dataType : "json",
                success : function(data) {
                    alert("success-->name:"+data.name+";age:"+data.age)
                } ,
                error:function(data){
                    alert("error-->name:"+data.name+";age:"+data.age)
                }
            });
        }

        function test4() {
            $.ajax( {
                type : "POST",//(默认: "GET") 请求方式 ("POST" 或 "GET")，
                url : "<%=basePath2%>addresscontroller/getaddress.do",
                data :'',
                async: false,
                dataType : "json",
                success : function(data) {
                    alert("success:"+data)
                } ,
                error:function(data){
                    alert("error:"+data)
                }
            });
        }
    </script>
</head>
<body style="text-align: left;margin-top: 20px">
<input type="button" onclick="test1(true)" value="测试1_execution切入点目标代码存在异常的情况"/><br><br>
<input type="button" onclick="test1(false)" value="测试1_execution切入点目标代码正常执行的情况"/><br><br>
<input type="button" onclick="test2('张三')" value="测试2_execution切入点目标代码正常执行的情况"/><br><br>
<input type="button" onclick="test2('李四')" value="测试2_execution切入点目标代码存在异常的情况"/><br><br>
<input type="button" onclick="test3()" value="测试3_不在切入点的情况"/><br><br>
<input type="button" onclick="test4()" value="测试4_Annotation切入点"/><br><br>

</body>
</html>
