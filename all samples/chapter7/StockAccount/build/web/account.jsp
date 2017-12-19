<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stocks</title>
    </head>
    <body background="coins.jpg">
        <div id="banner"></div>
        <h2 style="text-align: left;">Trading Site Account Information</h2>
        <table bgcolor="#ffffcc" border='0' cellpadding='2' cellspacing='2' width='360' valign="top">
            <tr>
                <td  align="center">
                    <table border='0' cellpadding='2' cellspacing='2' width='50%' valign="top">
                        <tr>
                            <td align="center"><br></td>
                        </tr>
                    </table>     
                   <table  border='0' cellpadding='2' cellspacing='2' width='50%' valign="top">     
                        <tr>
                            <td align="center"><a href="account.jsp">Trade</a></td>
                        </tr>
                        <tr>
                            <td align="center"><a href="account.jsp">Statements</a></td>
                        </tr>
                        <tr>
                            <td align="center"><a href="account.jsp">Gain/Loss</a></td>
                        </tr>
                        <tr>
                            <td align="center" ><a href="account.jsp">Research</a></td>
                        </tr>
                        <tr>
                            <td align="center"><a href="account.jsp">Tax</a></td>
                        </tr>
                        <tr>
                            <td align="center"><a href="account.jsp">Analyser</a></td>
                        </tr>
                        <tr>
                            <td align="center"><a href="account.jsp">Upgrade</a></td>
                        </tr>
                        <tr>
                            <td align="center"><a href="account.jsp">Questions</a></td>
                        </tr>
                    </table> 
                </td>
                <td>
                    <div valign="top" id="account"></div>
                </td>
                    
             
            </tr>
        
        
        </table>
        <br>
        <form action="">
            <input onclick="logout()" value="Logout" type="button">
        </form>
    </body>
    <script language="javascript" type="text/javascript">
            var accountWebSocket;
            var bannerWebSocket;
            var account;
            var banner;
            
            
            function init() {
                account = document.getElementById("account");
                banner = document.getElementById("banner");
                openBanner();
                openAccount();
            }
            
            function close() {
                bannerWebSocket.close();
                accountWebSocket.close();
            }
            
            function openBanner() {
                var uri = 'wss://localhost:8181/stockaccount/banner/secure';
                bannerWebSocket = new WebSocket(uri);
                bannerWebSocket.onmessage = function (evt) {
                    display(evt.data, banner)
                };
                bannerWebSocket.onclose = function(evt) {
                    display("updates closed", banner);
                }
            }
            
            function openAccount() {
                var uri = 'wss://localhost:8181/stockaccount/account/info';
                accountWebSocket = new WebSocket(uri);
                accountWebSocket.onmessage = function (evt) {
                    display(evt.data, account)
                };
                accountWebSocket.onclose = function(evt) {
                    display("updates closed", account);
                }
            }
            
            function logout() {
                window.open ("Logout","_self");
            }

            function display(data, node) {
                var pre = document.createElement("p");
                pre.style.wordWrap = "break-word";
                pre.innerHTML = data;
                clearNodes(node);
                node.appendChild(pre);
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
