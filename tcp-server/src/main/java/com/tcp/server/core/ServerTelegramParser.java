package com.tcp.server.core;

import java.io.IOException;
import java.net.Socket;

public interface ServerTelegramParser {
    Receiver receive(Socket socket) throws IOException;
    void send(Socket socket, Receiver receiver) throws IOException;
}
