package com.tcp.server.core.telegram;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestTelegram implements TelegramData {

    @Telegram(size = 4)
    private final String type;

    @Telegram(size = 10)
    private final String message;
}
