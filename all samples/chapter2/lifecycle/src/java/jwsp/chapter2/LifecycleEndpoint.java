package jwsp.chapter2;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


    @ServerEndpoint("/lights")
public class LifecycleEndpoint {
        private static String START_TIME = "Start Time";
        private Session session;
   
        @OnOpen
        public void whenOpening(Session session) {
            this.session = session;
            session.getUserProperties().put(START_TIME, System.currentTimeMillis());
            this.sendMessage("3:Just opened");
        }
        
        @OnMessage
        public void whenGettingAMessage(String message) {
            if (message.indexOf("xxx") != -1) {
                throw new IllegalArgumentException("xxx not allowed !");
            } else if (message.indexOf("close") != -1) {
                try {
                    this.sendMessage("1:Server closing after " + this.getConnectionSeconds() + " s");
                    session.close();
                } catch (IOException ioe) {
                    System.out.println("Error closing session " + ioe.getMessage());
                }
                return;
            }
            this.sendMessage("3:Just processed a message");
        }
        
        @OnError
        public void whenSomethingGoesWrong(Throwable t) {
            this.sendMessage("2:Error: " + t.getMessage());
        }
        
        @OnClose
        public void whenClosing() {
            System.out.println("Goodbye !"); 
        }
        
        void sendMessage(String message) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Throwable ioe) {
                System.out.println("Error sending message " + ioe.getMessage());
            }
        }
        
        int getConnectionSeconds() {
            long millis =  System.currentTimeMillis() - 
                ((Long) this.session.getUserProperties().get(START_TIME));
            return (int) millis / 1000;
        }
}
