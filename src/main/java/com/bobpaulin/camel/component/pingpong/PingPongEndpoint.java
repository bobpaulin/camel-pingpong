package com.bobpaulin.camel.component.pingpong;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.support.DefaultEndpoint;
import org.apache.camel.spi.UriParam;

public class PingPongEndpoint extends DefaultEndpoint {
    
    @UriParam
    private PingPongConfiguration pingPongConfiguration;

    public PingPongEndpoint(String endpointUri, PingPongComponent component, PingPongConfiguration pingPongConfiguration) {
        super(endpointUri, component);
    }
    
    @Override
    public Producer createProducer() throws Exception {
        return new PingPongProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        
        return new PingPongConsumer(this, processor);
    }
    
    public PingPongConfiguration getPingPongConfiguration() {
        return pingPongConfiguration;
    }

}
