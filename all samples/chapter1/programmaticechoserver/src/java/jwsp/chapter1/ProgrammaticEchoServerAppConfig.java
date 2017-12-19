package jwsp.chapter1;

import java.util.HashSet;
import java.util.Set;
import javax.websocket.Endpoint;
import javax.websocket.server.*;

public class ProgrammaticEchoServerAppConfig implements ServerApplicationConfig {
    
    @Override
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> endpointClasses) {
        Set configs = new HashSet<ServerEndpointConfig>();
        ServerEndpointConfig sec = ServerEndpointConfig.Builder
                                            .create(ProgrammaticEchoServer.class, "/programmaticecho")
                                            .build();
        configs.add(sec);
        return configs;  
    }

    @Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
        return scanned;
    }
}
