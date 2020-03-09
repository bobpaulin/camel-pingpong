package com.bobpaulin.camel.component.pingpong;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class PingPongTest extends CamelTestSupport {
    
    @Test
    public void testPingPong() throws Exception {   
    	String out = (String)template.requestBody("direct:start","Ping");
        
    	assertEquals("Body Should give Pong", "Pong", out);
    }
    
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start").to("pingpong:localhost:8181");
                
                from("pingpong:localhost:8181").to("mock:result");
            }
        };
    }
}
