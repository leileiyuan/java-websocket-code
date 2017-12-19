
package jwsp.chapter5.messagemodes;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import javax.websocket.*;
import javax.websocket.server.*;

    @ServerEndpoint(value="/modes")
public class MessageModesServer {
    public static final int MESSAGE_MAX =  15 * 1000 * 1024;   // 15, 000 kb
    
    @OnOpen
    public void open(Session session) {
        session.setMaxBinaryMessageBufferSize(MESSAGE_MAX);
        this.reportMessage(session, "Connected !");
    }
    
    @OnMessage
    public void binaryMessage(byte[] bytes, Session session) {
        this.reportMessage(session, "Processed binary message of length " + bytes.length / 1024 + "kb");  
    }

    @OnMessage(
        maxMessageSize = MESSAGE_MAX
    )
    public void textMessage(String partialMessage, boolean isLast, Session session) {
        String report = "Processed partial text message of size " + partialMessage.length() / 1024 + "kb...";
        if (isLast) {
            report = report + "message complete.";
        }
        this.reportMessage(session, report); 
    }
    
    public void reportMessage(Session s, String message) {
        try {            
            String timeStamp = DateFormat.getTimeInstance().format(new Date());
            s.getBasicRemote().sendText(timeStamp + " " + message);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
    
    @OnError
    public void error(Throwable t) {
        System.out.println("MessageModesServer error: " + t.getMessage());
    }
    
    @OnClose
    public void close(Session s, CloseReason cr) {
        System.out.println("MessageModesServer closing because: " + cr.getReasonPhrase());
    }
     
}
