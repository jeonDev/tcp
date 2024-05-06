package com.tcp.server.core;

import com.tcp.server.core.telegram.TelegramData;

public interface Receiver {
    byte[] getBytes();
    int getRead();
    TelegramData getData();
}
