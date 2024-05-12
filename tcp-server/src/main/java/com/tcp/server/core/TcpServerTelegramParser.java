package com.tcp.server.core;

import com.tcp.server.core.telegram.TelegramData;
import com.tcp.server.core.telegram.TelegramType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class TcpServerTelegramParser implements ServerTelegramParser {

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    @Value("${telegram.type-begin-index}")
    private Integer telegramTypeBeginIndex;

    @Value("${telegram.type-size}")
    private Integer telegramTypeSize;

    @Override
    public Receiver receive(Socket socket) throws IOException {
        InputStream is = socket.getInputStream();

        // read
        byte[] bytes = new byte[100];
        int read = is.read(bytes);
        String message = new String(bytes, 0, read, CHARSET);

        if (message.length() <= 4)
            throw new IllegalArgumentException("전문 파싱 에러");

        // parser
        try {
            log.info("Receive Message : {} ", message);
            String type = message.substring(telegramTypeBeginIndex, telegramTypeSize);
            TelegramData telegramObj = TelegramType.getType(type);
            TelegramData telegram = telegramObj.messageToTelegram(message);

            return TcpReceiver.builder()
                    .bytes(bytes)
                    .read(read)
                    .data(telegram)
                    .build();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void send(Socket socket, Receiver receiver) throws IOException {
        OutputStream os = socket.getOutputStream();
        os.write(receiver.getData().toString().getBytes());
        os.flush();
    }
}
