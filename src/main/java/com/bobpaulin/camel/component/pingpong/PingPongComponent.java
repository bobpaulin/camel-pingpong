package com.bobpaulin.camel.component.pingpong;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.support.DefaultComponent;
import org.apache.camel.spi.annotations.Component;

@Component("pingpong")
public class PingPongComponent extends DefaultComponent {

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        PingPongConfiguration pingPongConfiguration = new PingPongConfiguration();
        setProperties(pingPongConfiguration, parameters);
        
        pingPongConfiguration.setHost(uri);
        return new PingPongEndpoint(uri, this, pingPongConfiguration);
    }

}
