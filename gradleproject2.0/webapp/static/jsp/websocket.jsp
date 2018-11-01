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
        var socket;
        $(function() {
            $("#btnConnection").click(function() {
                //实现化WebSocket对象，指定要连接的服务器地址与端口
                if (window.WebSocket) {
                    socket = new WebSocket(encodeURI('ws://'+window.location.host+'/repository2018_2/websocket'));
                } else {
                    alert('提示', '当前浏览器不支持websocket，请更换浏览器');
                    return;
                }
                //发生了错误事件
                socket.onerror = function() {
                    alert("onerror:发生了错误");
                }
                //打开事件
                socket.onopen = function() {
                    alert("onopen:Socket 已打开");
                    socket.send("这是来自客户端的消息" + location.href + new Date());
                };

                //获得消息事件
                socket.onmessage = function(msg) {
                    alert("onmessage:"+msg.data);
                };
                //关闭事件
                socket.onclose = function() {
                    alert("onclose:Socket已关闭");
                };

            });

            $("#btnClose").click(function() {
                socket.close();
            });
        });
        function start() {
            socket.send('hello');
            return false;
        }
       /* var websocket = new WebSocket(encodeURI('ws://192.168.1.41:8080/repository2018_2/webSocketServer'));

        websocket.onopen = function (evnt) {
            document.getElementById('messages').innerHTML
                = 'Connection established';
        };
        websocket.onmessage = function (evnt) {
            document.getElementById('messages').innerHTML
                += '<br />' + event.data;
        };
        websocket.onerror = function (evnt) {
            alert('发生了错误:'+event)
        };
        websocket.onclose = function (evnt) {
            alert('关闭:'+event)
        };*/


    </script>
</head>
<body style="text-align: left;padding: 10px 0 0 10px">
  <input  type="button"  value="websever发送消息" style="background-color: red;color: white;size: 20px" id="btnConnection"/><br/><br/>
  <input type="button" value="Start" onclick="start()"/>
  <div id="messages"></div>

</body>
</html>
