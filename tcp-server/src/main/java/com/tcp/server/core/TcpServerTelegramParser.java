package com.tcp.server.core;

import com.tcp.server.core.telegram.TelegramData;
import com.tcp.server.core.telegram.TelegramType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

        // read
        byte[] bytes = new byte[100];
        int read = is.read(bytes);
        String message = new String(bytes, 0, read, CHARSET);

        if (message.length() <= 4)
            throw new IllegalArgumentException("전문 파싱 에러");

        // parser
        String type = message.substring(0, 4);
        Class<? extends TelegramData> telegramData = TelegramType.getType(type);
        try {
            Method method = telegramData.getMethod("messageToTelegram", telegramData);
            TelegramData data = (TelegramData) method.invoke(telegramData, message);
            log.info("Receive Message : {} ", message);

            return TcpReceiver.builder()
                    .bytes(bytes)
                    .read(read)
                    .data(data)
                    .build();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
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
