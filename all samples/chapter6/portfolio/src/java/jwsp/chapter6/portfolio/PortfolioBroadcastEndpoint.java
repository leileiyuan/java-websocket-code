package jwsp.chapter6.portfolio;

import java.io.*;
import java.util.*;
import javax.websocket.*;
import javax.websocket.server.*;

    @ServerEndpoint(
            value="/updates/{access-level}",
            encoders={PortfolioUpdateEncoder.class}
    )
public class PortfolioBroadcastEndpoint implements StockDataSourceListener {
        private Session session;
        private StockDataSource dataSource;
    
        @OnOpen
        public void startUpdates(Session session, @PathParam("access-level") String accessLevel) throws IOException   {
            this.session = session;
            MemberLevel memberLevel;
            memberLevel = MemberLevel.create(accessLevel);
            //Map<String, List<String>> requestParams = session.getRequestParameterMap();
            // workaround for bug in Glassfish parsing query string
            Map<String, List<String>> requestParams = QueryStringParser.parse(session.getQueryString());
            DataOptions options = this.parseOptionsFromRequestParams(requestParams);
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
        
        public DataOptions parseOptionsFromRequestParams(Map<String, List<String>> requestParams) {
            DataOptions options = (new DataOptions())
                    .currentPrice(requestParams.containsKey(DataOptions.CURRENT_PRICE))
                    .openPrice(requestParams.containsKey(DataOptions.OPEN_PRICE))
                    .percentChange(requestParams.containsKey(DataOptions.PERCENTAGE_CHANGE))
                    .volume(requestParams.containsKey(DataOptions.VOLUME));

            return options;
        }
        
}
