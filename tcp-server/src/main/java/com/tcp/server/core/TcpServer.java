package com.tcp.server.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TcpServer implements Server {

    private final int serverPort = 9999;
    private ServerSocket serverSocket;
    private final ServerTelegramParser tcpServerTelegramParser;

    @Override
    public void start() throws IOException {
        serverSocket = new ServerSocket(serverPort);
        while (true) {
            log.info("Tcp Server Start!");
            Socket socket = serverSocket.accept();
            SocketAddress isa = socket.getRemoteSocketAddress();
            InetSocketAddress isa1 = (InetSocketAddress) isa;
            log.info("Connect : {}", isa1.getHostName());

            Receiver receive = tcpServerTelegramParser.receive(socket);
            tcpServerTelegramParser.send(socket, receive);

            socket.close();
        }
    }

    @Override
    public void stop() throws IOException {
        serverSocket.close();
    }

    @Override
    public void destroy() throws Exception {
        this.stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.start();
    }
}
