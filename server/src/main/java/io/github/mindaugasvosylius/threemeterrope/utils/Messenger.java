package io.github.mindaugasvosylius.threemeterrope.utils;

public class Messenger {

    public static void send(String message, Object... args) {
        System.out.println(String.format(message, args));
    }
}
