package com.bobpaulin.camel.component.pingpong;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.support.DefaultConsumer;

public class PingPongConsumer extends DefaultConsumer {
    
    private URI pingPongUri;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public PingPongConsumer(Endpoint endpoint, Processor processor) throws URISyntaxException {
        super(endpoint, processor);
        pingPongUri = new URI(endpoint.getEndpointUri());
    }
    
    @Override
    protected void doStart() throws Exception {
        super.doStart();
        getEndpoint().getCamelContext().getExecutorServiceManager().newCachedThreadPool(this, "AsyncStartStopListener").submit(()->{
            try {
                serverSocket = new ServerSocket(pingPongUri.getPort());
            
                System.out.println("STarted");
                clientSocket = serverSocket.accept();
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String greeting = in.readLine();
                final Exchange exchange = getEndpoint().createExchange(ExchangePattern.InOut);
                exchange.getIn().setBody(greeting);
                getProcessor().process(exchange);
                String body = exchange.getOut().getBody(String.class);
                
                String response = null;
                if ("Ping".equals(body)) {
                    response = "Pong";
                }
                else {
                    response = "Error";
                }
                
                out.println(response);
            } catch ( Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }
    
    @Override
    protected void doStop() throws Exception {
        super.doStop();
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

}
