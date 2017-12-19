
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title id="titleID">Chat Client</title>
        <script language="javascript" type="text/javascript" src="chat_script.js">
        </script>

    </head>
    <body>
        <table>
        <tr>
            <td>
               
            </td>
        </tr>
    </table>
    <table style="text-align: center; " cellpadding="5" cellspacing="0" >
    <tbody>
    <tr>
        <td style=" vertical-align: top;" >
            <textarea id="transcriptID" rows="20" cols="50" readonly="readonly"></textarea>
            <br>
        </td>
        <td style="vertical-align: top;">
            <textarea id="userListID" rows="20" cols="15" readonly="readonly"></textarea>
            <br>
        </td>
    </tr>
    </tbody>
    </table>

    <table style=" text-align: center; " cellpadding="5"
           cellspacing="0" >
        <tbody>
        <tr>
            <td style=" vertical-align: top;">
                <form action="">
                    <input id="chatMessageTextID" size="70" name="chattext" value="Hi there !" type="text"><br>
                </form>

                <br>
            </td>
            <td style="vertical-align: top;">
                <table style=" text-align: left; " cellpadding="0"
                       cellspacing="2">
                    <tr>
                        <td style=" vertical-align: top;">
                            <form action=""><input type="button" id="SendButtonID" onclick="button_sendMessage()"
                                                   value="Send"></form>
                        </td>
                        <td style=" vertical-align: top;">
                            <form action=""><input type="button" id="SignInButtonID" onclick="button_signInOut()"
                                                   value="Sign in"></form>
                        </td>
                    </tr>
                </table>
                <br>
            </td>
        </tr>
        </tbody>
    </table>
    <div id="usernameID"></div>
    <br>
    <div id="moreID"><a href='data.jsp'>Find out more</a> about where your data is being held</div> 
    </body>
</html>
