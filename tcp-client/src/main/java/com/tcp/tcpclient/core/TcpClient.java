package com.tcp.tcpclient.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9999);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        String message = "Hi Hello";
        os.write(message.getBytes());
        os.flush();

        byte[] data = new byte[100];
        int read = is.read(data);
        String receiveMessage = new String(data, 0, read, StandardCharsets.UTF_8);
        System.out.println(receiveMessage);

    }
}
