package jwsp.chapter8.chat;

import jwsp.chapter8.data.UserListUpdateMessage;
import jwsp.chapter8.data.ChatUpdateMessage;
import jwsp.chapter8.data.NewUserMessage;
import jwsp.chapter8.data.ChatEncoder;
import jwsp.chapter8.data.ChatMessage;
import jwsp.chapter8.data.UserSignoffMessage;
import jwsp.chapter8.data.ChatDecoder;
import java.io.IOException;
import java.util.*;
import javax.websocket.*;
import javax.websocket.server.*;
import javax.servlet.http.*;
import javax.ejb.*;


@ServerEndpoint(value ="/chat-server",
        subprotocols={"chat-session"},
        decoders = {ChatDecoder.class},
        encoders = {ChatEncoder.class},
        configurator=ChatServerConfigurator.class)
public class ChatServer {
    public static String USERNAME_KEY = "username";
    private static String SESSION2HTTPSESSION = "http-session";
    private Session session;
    private HttpSession httpSession;
    @EJB
    private TranscriptBean transcriptBean;
    
    @OnOpen
    public void startChatChannel(EndpointConfig config, Session session) {
        ServerEndpointConfig endpointConfig = (ServerEndpointConfig) config;
        ChatServerConfigurator csc = (ChatServerConfigurator) endpointConfig.getConfigurator();
        this.session = session;
        this.httpSession = (HttpSession) config.getUserProperties().get(ChatServerConfigurator.HTTP_SESSION);
        this.session.getUserProperties().put(SESSION2HTTPSESSION, this.httpSession);
        if (this.httpSession.getAttribute(USERNAME_KEY) != null) {
            this.updateOutOfSyncClient();
        }
    }
    
    private void updateOutOfSyncClient() {
        this.confirmUser((String) this.httpSession.getAttribute(USERNAME_KEY));
        this.sendUserListUpdate(this.getUserList(), this.session);
        this.sendTranscript();
        this.addMessage(" and is now back !");
    }
    
    private void sendTranscript() {
       for (int i = 0; i < this.transcriptBean.getMessages().size(); i++) {
            String nextMessage = (String) this.transcriptBean.getMessages().get(i);
            String nextUsername = (String) this.transcriptBean.getMessageUsernames().get(i);
            this.sendTranscriptUpdate(nextUsername, nextMessage, this.session);
        }  
    }
    
    private void sendTranscriptUpdate(String username, String message, Session theSession) {
        ChatUpdateMessage cdm = new ChatUpdateMessage(username, message);
        try {
            theSession.getBasicRemote().sendObject(cdm);
        } catch (IOException | EncodeException ex) {
            System.out.println("Error updating a client : " + ex.getMessage());
        }
    }
    
    private void sendUserListUpdate(List<String> users, Session theSession) {
        UserListUpdateMessage ulum = new UserListUpdateMessage(users);
        try {
            theSession.getBasicRemote().sendObject(ulum);
        } catch (IOException | EncodeException ex) {
            System.out.println("Error updating a client : " + ex.getMessage());
        }
    }
    
    private void confirmUser(String username) {
        NewUserMessage uMessage = new NewUserMessage(username);
        try {
            session.getBasicRemote().sendObject(uMessage);
        } catch (IOException | EncodeException ioe) {
            System.out.println("Error signing " + uMessage.getUsername() + " into chat : " + ioe.getMessage());
        } 
    }

    @OnMessage
    public void handleChatMessage(ChatMessage message) {
        switch (message.getType()){
            case NewUserMessage.USERNAME_MESSAGE:
               this.processNewUser((NewUserMessage) message);
               break;
            case ChatMessage.CHAT_DATA_MESSAGE:
                this.processChatUpdate((ChatUpdateMessage) message);
                break;
            case ChatMessage.SIGNOFF_REQUEST:
                this.processSignoffRequest((UserSignoffMessage) message);
        }
    }
    
    @OnError
    public void myError(Throwable t) {
        System.out.println("Error: " + t.getMessage());
    }
    
    @OnClose
    public void endChatChannel() {
        if (this.getCurrentUsername() != null) {
            this.addMessage(" just stepped out for a minute...");
        }
    }

    void processNewUser(NewUserMessage message) {
        String newUsername = this.validateUsername(message.getUsername());
        this.confirmUser(newUsername); 
        this.registerUser(newUsername);
        this.broadcastUserListUpdate();
        this.sendTranscript();
        this.addMessage(" just joined.");
    }

    void processChatUpdate(ChatUpdateMessage message) {
        this.addMessage(message.getMessage());
    }

    void processSignoffRequest(UserSignoffMessage drm) {
        this.addMessage(" just left.");
        this.removeUser();   
    }
    
    private String getCurrentUsername() {
        String username = null;
        try {
            username = (String) this.httpSession.getAttribute(USERNAME_KEY);
        } catch (IllegalStateException ise) {
            // throws if the session has been invalidated or timed out
            // in this case we will null out the username
        }
        return username;
    }
    
    private void registerUser(String username) {
        this.httpSession.setAttribute(USERNAME_KEY, username);
        this.updateUserList();
    }
    
    private void updateUserList() {
        Set<String> usernames = new HashSet<>();
        for (Session s : session.getOpenSessions()) {
            HttpSession httpSession = (HttpSession) s.getUserProperties().get(SESSION2HTTPSESSION);
            String uname =(String) httpSession.getAttribute(USERNAME_KEY);
            usernames.add(uname);
        }
        this.transcriptBean.setActiveUsernames(new ArrayList(usernames));
    }
    
    private List<String> getUserList() {
        List<String> userList = this.transcriptBean.getActiveUsernames();
        return (userList == null) ? new ArrayList<String>() : userList;
    }

    
    private String validateUsername(String newUsername) {
        if (this.getUserList().contains(newUsername)) {
            return this.validateUsername(newUsername + "1");
        }
        return newUsername;
    }

    private void broadcastUserListUpdate() {
        for (Session nextSession : session.getOpenSessions()) {
            this.sendUserListUpdate(this.getUserList(), nextSession);  
        }
    }

    private void removeUser() {
        try {
            this.updateUserList();
            this.broadcastUserListUpdate();
            this.httpSession.removeAttribute(USERNAME_KEY);
            this.httpSession.invalidate();
            this.session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "User logged off"));
        } catch (IOException e) {
            System.out.println("Error removing user");
        }
    }

    private void broadcastTranscriptUpdate() {
        for (Session nextSession : session.getOpenSessions()) {
            this.sendTranscriptUpdate(this.transcriptBean.getLastMessageUsername(), this.transcriptBean.getLastMessage(), nextSession);
        }
    }

    private void addMessage(String message) {
        this.transcriptBean.addEntry(this.getCurrentUsername(), message);
        this.broadcastTranscriptUpdate();
    }
    


}
