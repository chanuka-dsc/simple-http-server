package com.simple.server.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);
    private Socket socket;


    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            String html = "<!DOCTYPE html> <html> <head><title>My server</title></head><body> <h1>Wel come to my page</h1> <p>Glad to see you here</p> </body> </html>";

            final String CRLF = "\n\r"; // 13, 10 ascii

            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line :HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF +//HEADER
                            CRLF +
                            html + // HTML
                            CRLF;


            outputStream.write(response.getBytes());


            LOGGER.info("processing finished !!!");
        } catch (IOException e) {
            LOGGER.info("Problem with communication", e);

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }
}
