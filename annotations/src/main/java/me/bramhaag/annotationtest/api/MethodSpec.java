package me.bramhaag.annotationtest.api;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import me.bramhaag.annotationtest.api.util.Modifier;
import me.bramhaag.annotationtest.api.util.ParserUtil;
import me.bramhaag.annotationtest.api.util.TypeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class MethodSpec implements ISpec {

    private String name;

    private Modifier[] modifiers;
    private Class<?>[] annotations;

    private Class<?> returnType;

    private Class<?>[] throwables;

    private Map<String, Class<?>> parameters;
    private Map<String, Queue<Object>> statements;

    private MethodSpec(Class<?> returnType, String name, Modifier[] modifiers, Class<?>[] annotations, Class<?>[] throwables,
                       Map<String, Class<?>> parameters, Map<String, Queue<Object>> statements) {
        this.name = name;
        this.modifiers = modifiers;
        this.annotations = annotations;
        this.returnType = returnType;
        this.throwables = throwables;
        this.parameters = parameters;
        this.statements = statements;
    }

    @Override
    public JCTree createTree(Context context) {
        TreeMaker treeMaker = TreeMaker.instance(context);
        JavacElements elements = JavacElements.instance(context);

        java.util.List<String> statements = this.statements.entrySet()
                .stream()
                .map(e -> parseStatement(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        StringBuilder methodBuilder = new StringBuilder();
        methodBuilder.append('{');
        statements.forEach(methodBuilder::append);
        methodBuilder.append('}');

        JCTree.JCBlock methodBody = ParserUtil.newParser(context, methodBuilder.toString()).block();

        long flags = Arrays.stream(modifiers)
                .mapToLong(Modifier::getValue)
                .reduce(0, (a, b) -> a | b);

        List<JCTree.JCAnnotation> annotations = List.from(Arrays.stream(this.annotations)
                .map(a -> treeMaker.Annotation(TypeUtil.getType(a, treeMaker, elements), List.nil()))
                .toArray(JCTree.JCAnnotation[]::new));

        JCTree.JCModifiers modifiers = treeMaker.Modifiers(flags, annotations);

        Name methodName = elements.getName(this.name);

        JCTree.JCExpression returnType = TypeUtil.getType(this.returnType, treeMaker, elements);

        List<JCTree.JCTypeParameter> genericParameters = List.nil();
        List<JCTree.JCVariableDecl> parameters = List.from(this.parameters.entrySet().stream()
                .map(e -> treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), elements.getName(e.getKey()), TypeUtil.getType(e.getValue(), treeMaker, elements), null))
                .toArray(JCTree.JCVariableDecl[]::new));

        System.out.println("Parameters: " + parameters);

        List<JCTree.JCExpression> throwables = List.from(Arrays.stream(this.throwables)
                .map(c -> ParserUtil.newParser(context, c.getName()).parseType())
                .toArray(JCTree.JCExpression[]::new));

        return treeMaker.MethodDef(modifiers, methodName, returnType, genericParameters, parameters, throwables, methodBody, null);
    }

    private String parseStatement(String statement, Queue<Object> args) {
        StringBuilder builder = new StringBuilder();

        int toReplace = (statement.length() - statement.replace("$L", "").length()) / 2;
        int replaced = 0;

        String[] parts = statement.split("\\$L");

        for(String part : parts) {
            builder.append(part);

            if(replaced >= toReplace) continue;

            Object arg = args.poll();
            System.out.println("Polled: " + arg.getClass().getName() + "=" + arg);
            if(arg instanceof String) {
                builder.append("\"").append(arg).append("\"");
            } else if (arg instanceof Integer) {
                builder.append(arg);
            } else if (arg instanceof Double) {
                builder.append(arg).append("D");
            } else if (arg instanceof Long) {
                builder.append(arg).append("L");
            } else if (arg instanceof Float) {
                builder.append(arg).append("f");
            } else if (arg instanceof Class<?>) {
                builder.append(((Class) arg).getName());
            } else {
                builder.append(arg);
            }

            replaced++;
        }

        statement = builder.toString();
        if(!statement.endsWith(";")) statement += ";";

        return statement;
    }

    public static class Builder {
        private Class<?> returnType = void.class;
        private String name;

        private java.util.List<Class<?>> annotations = new ArrayList<>();
        private java.util.List<Modifier> modifiers = new ArrayList<>();
        private java.util.List<Class<? extends Throwable>> throwables = new LinkedList<>();

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
