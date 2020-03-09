package com.bobpaulin.camel.component.pingpong;

import org.apache.camel.spi.UriParams;
import org.apache.camel.spi.UriPath;
import org.apache.camel.spi.Metadata;

@UriParams
public class PingPongConfiguration {
    @UriPath(description = "host")
    @Metadata(required = true)
    private String host;

    public String getHost() {
        return host;
    }
    
    public void setHost(String host) {
        this.host = host;
    }
}
