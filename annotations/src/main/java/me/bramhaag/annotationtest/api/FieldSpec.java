package me.bramhaag.annotationtest.api;

import me.bramhaag.annotationtest.api.util.Modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldSpec extends AbstractSpec {

    private Class<?> type;
    private Object value;

    private FieldSpec(Class<?> type, String name, Object value, Modifier[] modifiers, Class<?>[] annotations) {
        super(name, modifiers, annotations);

        this.type = type;
        this.value = value;
    }

    public Class<?> getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public static class Builder {
        private Class<?> type;
        private String name;
        private Object value;

        private List<Modifier> modifiers = new ArrayList<>();
        private List<Class<?>> annotations = new ArrayList<>();

        public Builder(Class<?> type, String name) {
            this.type = type;
            this.name = name;
        }

        public Builder setValue(Object value) {
            this.value = value;

            return this;
        }

        public Builder addModifiers(Modifier... modifiers) {
            this.modifiers.addAll(Arrays.asList(modifiers));

            return this;
        }

        public Builder addAnnotations(Class<?>... annotations) {
            this.annotations.addAll(Arrays.asList(annotations));

            return this;
        }

        public FieldSpec build() {
            return new FieldSpec(type, name, value, modifiers.toArray(new Modifier[0]), annotations.toArray(new Class<?>[0]));
        }
    }
}
