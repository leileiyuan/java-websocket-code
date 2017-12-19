package jwsp.chapter7.stockaccount;

import java.io.*;
import javax.websocket.*;
import javax.websocket.server.*;

    @ServerEndpoint(
            value="/banner/{secure-access}",
            encoders={PortfolioUpdateEncoder.class},
            configurator=MemberLevelConfigurator.class
    
    )
public class PortfolioBroadcastEndpoint implements StockDataSourceListener {
        private Session session;
        private StockDataSource dataSource;
    
        @OnOpen
        public void startUpdates(Session session, EndpointConfig ec) throws IOException   {
            this.session = session;
            MemberLevel memberLevel = (MemberLevel) ec.getUserProperties().get(MemberLevelConfigurator.MEMBER_LEVEL);
            DataOptions options = (new DataOptions())
                                    .currentPrice(true)
                                    .percentChange(true);
            this.dataSource = new StockDataSource(options, memberLevel);
            this.dataSource.addStockDataSourceListener(this);
            this.dataSource.start();
        }
        
        public void handleNewStockData(PortfolioUpdate pu)  {
            
            try {
                session.getBasicRemote().sendObject(pu);
            } catch (IOException | EncodeException ioe) {
                this.processError(ioe);
            }
        }
        
        @OnError
        public void processError(Throwable t) {
            System.out.println("Error: " + t.getMessage());
        } 
        
        @OnClose
        public void stopUpdates(Session session) {
            dataSource.stop();
        }
        
}
