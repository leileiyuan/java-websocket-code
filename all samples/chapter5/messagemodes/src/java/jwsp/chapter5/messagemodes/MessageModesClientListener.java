
package jwsp.chapter5.messagemodes;

import javax.websocket.*;

public interface MessageModesClientListener {
    public void setConnected(boolean isConnected, CloseReason cr);
    public void reportMessage(String message);
    public void reportConnectionHealth(long millis);
}
