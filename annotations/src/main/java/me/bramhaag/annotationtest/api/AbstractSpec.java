package me.bramhaag.annotationtest.api;

import me.bramhaag.annotationtest.api.util.Modifier;

public abstract class AbstractSpec {

    private String name;

    private Modifier[] modifiers;
    private Class<?>[] annotations;

    public AbstractSpec(String name, Modifier[] modifiers, Class<?>[] annotations) {
        this.name = name;
        this.modifiers = modifiers;
        this.annotations = annotations;
    }

    public String getName() {
        return name;
    }

    public Modifier[] getModifiers() {
        return modifiers;
    }

    public Class<?>[] getAnnotations() {
        return annotations;
    }
}
