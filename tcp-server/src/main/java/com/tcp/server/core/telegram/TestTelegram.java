package com.tcp.server.core.telegram;

public class TestTelegram implements TelegramData {

    @Telegram(size = 4)
    private String type;

    @Telegram(size = 10)
    private String message;
}
