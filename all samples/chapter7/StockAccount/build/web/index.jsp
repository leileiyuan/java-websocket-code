
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stocks</title>
    </head>
    <body background="coins.jpg">
        <div id="output"></div>
        <h2 style="text-align: left;">Welcome to the Trading Site </h2>
        <img alt="asasd" src="coins_bold.jpg" height="270" width="360">
        <form action="">
            <input onclick="login()" value="Login" type="button">
        </form>

    </body>
    <script language="javascript" type="text/javascript">
            var bannerWebSocket;
            
            function init() {
                output = document.getElementById("output");
                openBanner();
            }
            
            function close() {
                bannerWebSocket.close()
            }
            
            function login() {
                window.open ("account.jsp","_self");
            }
            
            function openBanner() {
                var uri = 'wss://localhost:8181/stockaccount/banner/nouser';
                bannerWebSocket = new WebSocket(uri);
                bannerWebSocket.onmessage = function (evt) {
                    display(evt.data)
                };
                bannerWebSocket.onclose = function(evt) {
                    display("");
                } 
            }

            function display(data) {
                var pre = document.createElement("p");
                pre.style.wordWrap = "break-word";
                pre.innerHTML = data;
                clearNodes(output);
                output.appendChild(pre);
            }
            
            function clearNodes(node) {
                if (node.firstChild != null) {
                    node.removeChild(node.firstChild);
                }
            }
            
            window.addEventListener("beforeunload", close, false);
            window.addEventListener("load", init, false);
        </script>
</html>
