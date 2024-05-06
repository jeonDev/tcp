package com.tcp.server.core;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TcpReceiver implements Receiver {
    private final byte[] data;
    private final int read;
    private final String message;
}
