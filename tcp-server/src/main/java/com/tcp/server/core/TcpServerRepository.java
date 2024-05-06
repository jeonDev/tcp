package com.tcp.server.core;

import org.springframework.stereotype.Component;

import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

@Component
public class TcpServerRepository {
    private final Set<Socket> sockets = new HashSet<>();

    public void addSocket(Socket socket) {
        sockets.add(socket);
    }

    public void removeSocket(Socket socket) {
        sockets.remove(socket);
    }
}
