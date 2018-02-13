package com.opreaalex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Application {

    public static void main(final String[] args) {
        try {
            final Socket socket = new Socket("localhost", 8282);
            final InputStream inputStream = socket.getInputStream();
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
