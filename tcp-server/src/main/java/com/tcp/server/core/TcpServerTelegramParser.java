package com.tcp.server.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class TcpServerTelegramParser implements ServerTelegramParser {

    private static final Charset CHARSET = StandardCharsets.UTF_8;
    @Override
    public Receiver receive(Socket socket) throws IOException {
        InputStream is = socket.getInputStream();

        byte[] data = new byte[100];
        int read = is.read(data);
        String message = new String(data, 0, read, CHARSET);
        log.info("Receive Message : {} ", message);

        return TcpReceiver.builder()
                .data(data)
                .read(read)
                .message(message)
                .build();
    }

    @Override
    public void send(Socket socket, Receiver receiver) throws IOException {
        OutputStream os = socket.getOutputStream();
        os.write(receiver.getMessage().getBytes());
        os.flush();
    }
}
