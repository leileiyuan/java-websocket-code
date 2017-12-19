package jwsp.chapter3.drawing.server;

import java.awt.Color;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import jwsp.chapter3.drawing.data.DrawingAsIterator;
import jwsp.chapter3.drawing.data.DrawingFromReader;
import jwsp.chapter3.drawing.data.DrawingObject;

    @ServerEndpoint("/draw")
public class DrawingServer {
    private Session session;
    
    @OnOpen
    public void initSession(Session session) {
        this.session = session;
    }
   
    @OnMessage
    public void shapeCreated(Reader reader) {
        DrawingObject drawingObject;
        try (Reader rdr = reader) {
            DrawingFromReader dp = new DrawingFromReader(rdr);
            drawingObject = dp.getDrawingObject();
 
        } catch (IOException e) {
            System.out.println("There was an error reading the incoming message.");
            return;
        } 
        DrawingObject toSend = new DrawingObject(
                                drawingObject.getShape(), 
                                drawingObject.getCenter(),
                                drawingObject.getRadius(),
                                this.getFadedColor(drawingObject.getColor()));
        for (Session otherSession : this.session.getOpenSessions()) {
            if (!otherSession.equals(this.session)) {
                try {
                    DrawingAsIterator dai = new DrawingAsIterator(toSend);
                    sendDrawing(otherSession, dai);
                } catch (IOException ioe) {
                    System.out.println("Communication error: " + ioe.getMessage());
                }
            }
        }
    }
    
    private Color getFadedColor(Color c) {
        Color faded = new Color((int) 255 - ((255-c.getRed()) / 2), 
                            (int) 255 - ((255-c.getGreen()) / 2), 
                            (int) 255 - ((255-c.getBlue()) / 2));
        return faded;  
    }
    
        @OnError
    public void handleError(Throwable thw) {
         if (thw instanceof DecodeException) {
            System.out.println("Error decoding incoming message: " + ((DecodeException)thw).getText());
        } else {
            System.out.println("Server WebSocket error: " + thw.getMessage());
        }
    }
    
    private void sendDrawing(Session aSession, Iterator<String> drawingAsIterator) throws IOException {
        RemoteEndpoint.Basic remote = aSession.getBasicRemote();
        while (drawingAsIterator.hasNext()) {
            String partialMessage = drawingAsIterator.next();
            boolean isLast = !drawingAsIterator.hasNext();
            remote.sendText(partialMessage, isLast);
        } 
    }

}
