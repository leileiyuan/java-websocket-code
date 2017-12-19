<%-- 
    Document   : data
    Created on : Jun 3, 2013, 4:34:22 PM
    Author     : dannycoward
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data for <jsp:include page="NameServlet"/></title>
    </head>
    <body>
        Hello <jsp:include page="NameServlet"/>here is a breakdown of the chat data<br><br>
        You can <a href="index.jsp">return</a> to your paused chat session whenever you
        like<br>
        
        
        <h2>Current Information</h2>
        <table border="0" cellpadding="2" cellspacing="2" width="500">
            <tbody>
                <tr>
                    <td bgcolor="#99ff99" align ="center"valign="center">Current User<br>
                    </td>
                    <td bgcolor="#99ff99" align ="center"valign="center">Complete Transcript<br>
                    </td>
                    <td bgcolor="#99ff99" align ="center"valign="center">Active Users<br>
                    </td>
                </tr>
                <tr>
                    <td bgcolor="#ffffcc" align ="center"valign="center"><jsp:include page="NameServlet" /><br>
                    </td>
                    <td bgcolor="#ffffcc" align ="center"valign="center"><jsp:include page="TranscriptServlet" /><br>
                    </td>
                    <td bgcolor="#ffffcc" align ="center"valign="center"><jsp:include page="UsernamesServlet" /><br>
                    </td>
                </tr>
            </tbody>
        </table>
        
        <h2>Messages, by user</h2>            
        <jsp:include page="UserMessagesServlet" />
    </body>
</html>
