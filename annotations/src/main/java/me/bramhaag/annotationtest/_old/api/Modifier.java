package me.bramhaag.annotationtest._old.api;

import com.sun.tools.javac.code.Flags;

public enum Modifier {
    PUBLIC(Flags.PUBLIC),
    PRIVATE(Flags.PRIVATE),
    PROTECTED(Flags.PROTECTED),
    STATIC(Flags.STATIC),
    FINAL(Flags.FINAL),
    SYNCHRONIZED(Flags.SYNCHRONIZED);

    private int value;

    Modifier(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
