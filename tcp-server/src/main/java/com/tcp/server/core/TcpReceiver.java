package com.tcp.server.core;

import com.tcp.server.core.telegram.TelegramData;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TcpReceiver<T extends TelegramData> implements Receiver {
    private final byte[] bytes;
    private final int read;
    private final T data;
}
