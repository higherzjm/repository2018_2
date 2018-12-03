<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"+request.getServerName() + ":" + request.getServerPort() + path + "/style/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
    <script src="js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
        var websocket1,websocket2;
        $(function() {
            $("#btnConnection").click(function() {
                //实现化WebSocket对象，指定要连接的服务器地址与端口
                if (window.WebSocket) {
                    websocket1 = new WebSocket(encodeURI('ws://127.0.0.1:8080/repository2018_2/websocket'));
                } else {
                    alert('提示', '当前浏览器不支持websocket，请更换浏览器');
                    return;
                }
                //发生了错误事件
                websocket1.onerror = function() {
                    alert("onerror:发生了错误");
                }
                //打开事件
                websocket1.onopen = function() {
                    alert("onopen:Socket 已打开");
                    socket.send("这是来自客户端的消息" + location.href + new Date());
                };

                //获得消息事件
                websocket1.onmessage = function(msg) {
                    alert("onmessage:"+msg.data);
                };
                //关闭事件
                websocket1.onclose = function() {
                    alert("onclose:Socket已关闭");
                };

            });

            $("#btnConnection2").click(function() {
                websocket2 = new WebSocket(encodeURI('ws://127.0.0.1:8081/Batch/websocket'));//ws://180.153.201.159:8080/
                websocket2.onopen = function() {
                    //连接成功
                    alert("onopen连接成功");
                }
                websocket2.onerror = function() {
                    alert("onerror");
                }
                websocket2.onclose = function() {
                    alert("onclose");
                }
                //消息接收
                websocket2.onmessage = function(message) {
                    alert("接收消息:"+message)
                }

            });
        });
        function send1() {
            socket1.send('hello');
            return false;
        }
        function send2() {
            socket2.send('hello');
            return false;
        }


    </script>
</head>
<body style="text-align: left;padding: 10px 0 0 10px">
  <input  type="button"  value="连接1" style="background-color: red;color: white;size: 20px" id="btnConnection"/>&nbsp;
  <input type="button" value="发送消息1" onclick="send1()"/>
<br><br>
  <input  type="button"  value="连接2" style="background-color: red;color: white;size: 20px" id="btnConnection2"/>&nbsp;
  <input type="button" value="发送消息2" onclick="send2()"/>

</body>
</html>
