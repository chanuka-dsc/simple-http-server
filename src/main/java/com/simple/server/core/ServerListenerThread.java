package com.simple.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends  Thread {

    private int port;
    private String webRoot;
    ServerSocket serverSocket;

    public ServerListenerThread(int port, String webRoot) throws IOException {
        this.port = port;
        this.webRoot = webRoot;
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String html = "<!DOCTYPE html> <html> <head><title>My server</title></head><body> <h1>Wel come to my page</h1> <p>Glad to see you here</p> </body> </html>";

            final String CRLF = "\n\r"; // 13, 10 ascii

            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line :HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF +//HEADER
                            CRLF +
                            html + // HTML
                            CRLF;

            outputStream.write(response.getBytes());
            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
