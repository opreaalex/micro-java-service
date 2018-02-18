package com.opreaalex;

import com.opreaalex.parser.BetMessageParser;
import com.opreaalex.service.BetService;
import com.opreaalex.store.RabbitMqBetStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Application {

    private static final String STREAMER_HOST = "localhost";
    private static final int STREAMER_PORT = 8282;

    public static void main(final String[] args) {
        final BetService service = new BetService();
        service.setParser(new BetMessageParser());
        service.setStore(new RabbitMqBetStore());

        try {
            final Socket socket = new Socket(STREAMER_HOST, STREAMER_PORT);
            final InputStream inputStream = socket.getInputStream();
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                service.onNewLine(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
