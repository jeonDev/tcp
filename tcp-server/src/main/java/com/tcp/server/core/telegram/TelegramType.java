package com.tcp.server.core.telegram;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TelegramType {

    TEST("0000", TestTelegram.class);

    private final String type;
    private final Class<? extends TelegramData> dataType;

    public static Class<? extends TelegramData> getType(String type) {
        for (TelegramType t : TelegramType.values()) {
            if (type.equals(t.getType())) return t.getDataType();
        }
        throw new AssertionError("Not Exists Type");
    }
}
