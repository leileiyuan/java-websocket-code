package jwsp.chapter7.stockaccount;

import javax.websocket.*;
import javax.websocket.server.*;

public class MemberLevelConfigurator extends ServerEndpointConfig.Configurator {
    public static String CUSTOMER = "customer";
    public static String PREMIUM_CUSTOMER = "premium_customer";
    public static String MEMBER_LEVEL = "MemberLevel";
    
    @Override
    public void modifyHandshake(ServerEndpointConfig sec,
                   HandshakeRequest request,
                   HandshakeResponse response) {
        MemberLevel ml;
        if (request.isUserInRole(CUSTOMER)) {
            ml = MemberLevel.SILVER;
        } else if (request.isUserInRole(PREMIUM_CUSTOMER)) {
            ml = MemberLevel.GOLD;
        } else {
            ml = MemberLevel.BRONZE;
        }
        sec.getUserProperties().put(MEMBER_LEVEL, ml);
    } 
    
}
