
package jwsp.chapter8.chat;

import java.util.*;
import javax.ejb.Singleton;
import javax.ejb.Lock;
import static javax.ejb.LockType.WRITE;


@Singleton
@Lock(WRITE)
public class TranscriptBean {
    private Date time;
    private List<String> messages = new ArrayList<>();
    private List<String> messageUsernames = new ArrayList<>();
    private int maxLines = 20;
    private List<String> activeUsernames = new ArrayList<>();
    
    public TranscriptBean() {
        this.time = new Date();
    }
    
    public void setActiveUsernames(List<String> usernames) {
        this.activeUsernames = usernames;
    }
    
    public void notifyHttpSessionInvalidated(String username) {
        
    }
    
    public List<String> getActiveUsernames() {
        return this.activeUsernames;
    }

    public String getMessage() {
        return "Singleton ("+ time + ") !";
    }

    
    public List<String> getTranscriptFor(String username) {
        List<String> recording = new ArrayList<>();
        for (int i = 0; i < this.messageUsernames.size(); i++) {
            String uName = this.messageUsernames.get(i);
            if (uName.equals(username)) {
                recording.add(this.messages.get(i));
            }
        }
        return recording;
    }
    
    public String getLastMessageUsername() {
        return messageUsernames.get(messageUsernames.size() -1);
    }
    
    public String getLastMessage() {
        return messages.get(messages.size() -1);
    }
    
    public List<String> getMessages() {
        return new ArrayList(this.messages);
    }
    
    public List<String> getMessageUsernames() {
        return new ArrayList(this.messageUsernames);
    }

    public void addEntry(String username, String message) {
        
        if (messageUsernames.size() > maxLines) {
            messageUsernames.remove(0);
            messages.remove(0);
        }
        messageUsernames.add(username);
        messages.add(message);

            
    }

}
