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

            String tempTelegram = message.substring(i, i + size);
            i = i + size;
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

    default String generateTelegram() throws IllegalAccessException {
        StringBuilder strBuilder = new StringBuilder();
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            Telegram telegram = field.getAnnotation(Telegram.class);
            int size = telegram.size();
            Class<?> type = telegram.type();
            field.setAccessible(true);
            Object o = field.get(this);

            if (type == int.class) {
                String format = String.format("%0" + size + "d", o);
                strBuilder.append(format);
            } else if (type == String.class){
                strBuilder.append(o);
            }
        }

        return strBuilder.toString();
    }
}
