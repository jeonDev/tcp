package com.tcp.server.core.telegram;

public class SecondTelegram implements TelegramData {

    @Telegram(size = 4)
    private String type;

    @Telegram(type = int.class, size = 10)
    private int size;
}
