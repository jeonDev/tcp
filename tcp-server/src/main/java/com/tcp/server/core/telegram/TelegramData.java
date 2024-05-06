package com.tcp.server.core.telegram;

public interface TelegramData {

    default TelegramData messageToTelegram(String message) {

        return null;
    }

    default String generateTelegram() {
        return "";
    }
}
