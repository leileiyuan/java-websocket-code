package jwsp.chapter8.chat;

import javax.websocket.*;
import javax.websocket.server.*;
import javax.servlet.http.*;
import java.util.*;


public class ChatServerConfigurator extends ServerEndpointConfig.Configurator {
    public static String HTTP_SESSION = "Http-session";
    
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        sec.getUserProperties().put(HTTP_SESSION, request.getHttpSession());
    }
    
   
      
}
