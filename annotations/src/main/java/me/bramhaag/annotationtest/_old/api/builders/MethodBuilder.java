package me.bramhaag.annotationtest._old.api.builders;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.parser.JavacParser;
import com.sun.tools.javac.parser.ParserFactory;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import me.bramhaag.annotationtest._old.api.Modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static com.sun.tools.javac.tree.JCTree.JCAnnotation;
import static com.sun.tools.javac.tree.JCTree.JCBlock;
import static com.sun.tools.javac.tree.JCTree.JCExpression;
import static com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import static com.sun.tools.javac.tree.JCTree.JCModifiers;
import static com.sun.tools.javac.tree.JCTree.JCTypeParameter;
import static com.sun.tools.javac.tree.JCTree.JCVariableDecl;

public class MethodBuilder {
    private String methodName;
    private TreeMaker treeMaker;
    private JavacElements elements;

    private ParserFactory parserFactory;

    //TODO primitives
    private Class<?> returnType = void.class;

    private java.util.List<Class<?>> annotations = new ArrayList<>();
    private java.util.List<Modifier> modifiers = new ArrayList<>();
    private java.util.List<Class<? extends Throwable>> throwables = new LinkedList<>();

    private Map<String, Queue<Object>> statements = new LinkedHashMap<>();
    private Map<String, Class<?>> parameters = new LinkedHashMap<>();

    public MethodBuilder(String name, Context context) {
        this.methodName = name;
        this.treeMaker = TreeMaker.instance(context);
        this.elements = JavacElements.instance(context);
        this.parserFactory = ParserFactory.instance(context);
    }

    public MethodBuilder setReturnType(Class<?> returnType) {
        this.returnType = returnType;

        return this;
    }

    public MethodBuilder addAnnotations(Class<?>... annotations) {
        this.annotations.addAll(Arrays.asList(annotations));

        return this;

    }

    public MethodBuilder addModifiers(Modifier... modifiers) {
        this.modifiers.addAll(Arrays.asList(modifiers));

        return this;
    }

    public MethodBuilder addParameter(Class<?> type, String name) {
        this.parameters.put(name, type);

        return this;
    }

    public MethodBuilder addStatement(String statement, Object... args) {
        LinkedList<Object> objects = new LinkedList<>();
        objects.addAll(Arrays.asList(args));

        statements.put(statement, objects);

        return this;
    }

    public MethodBuilder throwsThrowable(Class<? extends Throwable> throwable) {
        this.throwables.add(throwable);

        return this;

    }

    public JCMethodDecl build() {
        java.util.List<String> stats = new ArrayList<>();
        for (Map.Entry<String, Queue<Object>> entry : this.statements.entrySet()) {
            String statement = entry.getKey();
            Queue<Object> args = entry.getValue();

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

            stats.add(statement);
        }

        StringBuilder methodBuilder = new StringBuilder();
        methodBuilder.append('{');
        stats.forEach(methodBuilder::append);
        methodBuilder.append('}');

        JCBlock methodBody = newParser(methodBuilder.toString()).block();

        long flags = this.modifiers.stream()
                .mapToLong(Modifier::getValue)
                .reduce(0, (a, b) -> a | b);

        List<JCAnnotation> annotations = List.from(this.annotations.stream()
                .map(a -> treeMaker.Annotation(getType(a), List.nil()))
                .toArray(JCAnnotation[]::new));

        JCModifiers modifiers = treeMaker.Modifiers(flags, annotations);

        Name methodName = elements.getName(this.methodName);

        JCExpression returnType = getType(this.returnType);

        List<JCTypeParameter> genericParameters = List.nil();
        List<JCVariableDecl> parameters = List.from(this.parameters.entrySet().stream()
                .map(e -> treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), elements.getName(e.getKey()), getType(e.getValue()), null))
                .toArray(JCVariableDecl[]::new));

        System.out.println("Parameters: " + parameters);

        List<JCExpression> throwables = List.from(this.throwables.stream()
                .map(c -> newParser(c.getName()).parseType())
                .toArray(JCExpression[]::new));

        JCMethodDecl methodDecl = treeMaker.MethodDef(modifiers, methodName, returnType, genericParameters, parameters, throwables, methodBody, null);
        return methodDecl;
    }

    private JavacParser newParser(String code) {
        return parserFactory.newParser(code, true, true, true, true);
    }

//    private JCStatement parseStatement(String statement) {
//        System.out.println("Statement in: " + statement);
//        JavacParser parser = parserFactory.newParser(statement, true, true, true, true);
//        System.out.println("Statement out: " + parser.block());
//        return parser.parseStatement();
//    }

    private JCExpression getType(Class<?> clazz) {
        JCExpression expr;

        if(clazz.isArray()) {
            int dims = 0;
            while (clazz.isArray()) {
                clazz = clazz.getComponentType();
                dims++;
            }

            expr = getTypeClass(clazz);

            for(int i = 0; i < dims; i++) {
                expr = treeMaker.TypeArray(expr);
            }
        } else {
            expr = getTypeClass(clazz);
        }

        return expr;
    }

    private JCExpression getTypeClass(Class<?> clazz) {
        String[] arr = clazz.getName().split("\\.");
        JCExpression expr = treeMaker.Ident(elements.getName(arr[0]));

        for (int i = 1; i < arr.length; i++) {
            expr = treeMaker.Select(expr, elements.getName(arr[i]));
        }

        return expr;
    }

    /*method.body = treeMaker.Block(0, List.of(
        treeMaker.Exec(
                treeMaker.Apply(
                        List.nil(),
                        treeMaker.Select(
                                treeMaker.Select(treeMaker.Ident(elements.getName("System")), elements.getName("out")), elements.getName("println")
                        ),
                        List.of(treeMaker.Literal("Hello, world"))
                )
        )
        )
);*/
}