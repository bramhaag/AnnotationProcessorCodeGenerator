package me.bramhaag.annotationtest.api;

import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import me.bramhaag.annotationtest.api.util.Modifier;
import me.bramhaag.annotationtest.api.util.TypeUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class FieldSpec implements ISpec {

    private String name;

    private Modifier[] modifiers;
    private Class<?>[] annotations;

    private Class<?> type;
    private Object value;

    private FieldSpec(Class<?> type, String name, Object value, Modifier[] modifiers, Class<?>[] annotations) {
        this.name = name;
        this.modifiers = modifiers;
        this.annotations = annotations;

        this.type = type;
        this.value = value;
    }

    @Override
    public JCTree createTree(Context context, JCTree.JCClassDecl classDecl) {
        TreeMaker treeMaker = TreeMaker.instance(context);
        JavacElements elements = JavacElements.instance(context);

        long flags = Arrays.stream(this.modifiers)
                .mapToLong(Modifier::getValue)
                .reduce(0, (a, b) -> a | b);

        List<JCTree.JCAnnotation> annotations = List.from(Arrays.stream(this.annotations)
                .map(a -> treeMaker.Annotation(TypeUtil.getType(a, treeMaker, elements), com.sun.tools.javac.util.List.nil()))
                .toArray(JCTree.JCAnnotation[]::new));

        JCTree.JCModifiers modifiers = treeMaker.Modifiers(flags, annotations);

        return treeMaker.VarDef(modifiers, elements.getName(name), TypeUtil.getType(type, treeMaker, elements), treeMaker.Literal(value));
    }

    public static class Builder {
        private Class<?> type;
        private String name;
        private Object value;

        private java.util.List<Modifier> modifiers = new ArrayList<>();
        private java.util.List<Class<?>> annotations = new ArrayList<>();

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
