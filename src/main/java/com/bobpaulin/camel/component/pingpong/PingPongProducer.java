package com.bobpaulin.camel.component.pingpong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultProducer;

public class PingPongProducer extends DefaultProducer {
    
    private URI pingPongUri;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public PingPongProducer(PingPongEndpoint endpoint) throws UnknownHostException, IOException, URISyntaxException {
        super(endpoint);
        pingPongUri = new URI(endpoint.getEndpointUri());
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);
        System.out.println("Host: " + pingPongUri.getHost() + " Port: " + pingPongUri.getPort());
        clientSocket = new Socket(pingPongUri.getHost(), pingPongUri.getPort());
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        if(body == null) {
            body = "Ping";
        }
        out.println(body);
        String resp = in.readLine();
        exchange.getIn().setBody(resp);
        in.close();
        out.close();
        clientSocket.close();
    }

}
