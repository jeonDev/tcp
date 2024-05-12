package com.tcp.server.core.telegram;

import java.lang.reflect.Field;

public interface TelegramData {

    default TelegramData messageToTelegram(String message) throws IllegalAccessException {
        int i = 0;
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            Telegram telegram = field.getAnnotation(Telegram.class);
            Class<?> type = telegram.type();
            int size = telegram.size();
            field.setAccessible(true);

            String tempTelegram = message.substring(i, size);

            if (type == int.class) {
                field.setInt(this, Integer.parseInt(tempTelegram));
            } else if (type == String.class) {
                field.set(this, tempTelegram);
            } else {
                throw new RuntimeException();
            }
        }
        return this;
    }

    default String generateTelegram() {
        return "";
    }
}
