package com.tcp.server.core.telegram;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Getter
@AllArgsConstructor
public enum TelegramType {

    TEST("0000", TestTelegram.class),
    SECOND("0001", SecondTelegram.class);

    private final String type;
    private final Class<? extends TelegramData> dataType;

    public static TelegramData getType(String type) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (TelegramType t : TelegramType.values()) {
            if (type.equals(t.getType())) {
                Constructor<? extends TelegramData> constructor = t.getDataType().getDeclaredConstructor();
                return constructor.newInstance();
            }
        }
        throw new AssertionError("Not Exists Type");
    }
}
