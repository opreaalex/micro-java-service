package com.opreaalex;

import com.opreaalex.parser.BetMessageParser;
import com.opreaalex.parser.StreamLineParser;
import com.opreaalex.parser.exception.StreamLineParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Application {

    public static void main(final String[] args) {
        final StreamLineParser parser = new BetMessageParser();

        try {
            final Socket socket = new Socket("localhost", 8282);
            final InputStream inputStream = socket.getInputStream();
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(parser.parseLine(inputLine));
            }
        } catch (IOException | StreamLineParserException e) {
            e.printStackTrace();
        }
    }
}
