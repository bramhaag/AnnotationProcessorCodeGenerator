package me.bramhaag.annotationtest.api;

import me.bramhaag.annotationtest.api.util.Modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MethodSpec extends AbstractSpec {

    private Class<?> returnType;

    private Class<?>[] throwables;

    private Map<String, Class<?>> parameters;
    private Map<String, Queue<Object>> statements;

    private MethodSpec(Class<?> returnType, String name, Modifier[] modifiers, Class<?>[] annotations, Class<?>[] throwables,
                       Map<String, Class<?>> parameters, Map<String, Queue<Object>> statements) {
        super(name, modifiers, annotations);

        this.returnType = returnType;
        this.throwables = throwables;
        this.parameters = parameters;
        this.statements = statements;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Class<?>[] getThrowables() {
        return throwables;
    }

    public Map<String, Class<?>> getParameters() {
        return parameters;
    }

    public Map<String, Queue<Object>> getStatements() {
        return statements;
    }

    public static class Builder {
        private Class<?> returnType = void.class;
        private String name;

        private List<Class<?>> annotations = new ArrayList<>();
        private List<Modifier> modifiers = new ArrayList<>();
        private List<Class<? extends Throwable>> throwables = new LinkedList<>();

        private Map<String, Class<?>> parameters = new LinkedHashMap<>();
        private Map<String, Queue<Object>> statements = new LinkedHashMap<>();

        public Builder(String name) {
            this.name = name;
        }

        public Builder setReturnType(Class<?> returnType) {
            this.returnType = returnType;

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

        public Builder addThrowables(Class<? extends Throwable>... throwables) {
            this.throwables.addAll(Arrays.asList(throwables));

            return this;
        }

        public Builder addParameter(Class<?> type, String name) {
            this.parameters.put(name, type);

            return this;
        }

        public Builder addStatement(String statement, Object... args) {
            LinkedList<Object> objects = new LinkedList<>();
            objects.addAll(Arrays.asList(args));

            this.statements.put(statement, objects);

            return this;
        }

        public MethodSpec build() {
            return new MethodSpec(returnType, name, modifiers.toArray(new Modifier[0]), annotations.toArray(new Class<?>[0]),
                    throwables.toArray(new Class<?>[0]), parameters, statements);
        }

    }
}
