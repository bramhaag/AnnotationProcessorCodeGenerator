package me.bramhaag.annotationtest._old.api.builders;

import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import me.bramhaag.annotationtest._old.api.Modifier;
import me.bramhaag.annotationtest._old.api.TypeUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class FieldBuilder {

    private TreeMaker treeMaker;
    private JavacElements elements;

    private String fieldName;

    private Class<?> type;
    private Object value;

    private java.util.List<Class<?>> annotations = new ArrayList<>();
    private java.util.List<Modifier> modifiers = new ArrayList<>();

    public FieldBuilder(String fieldName, Context context) {
        this.fieldName = fieldName;
        this.treeMaker = TreeMaker.instance(context);
        this.elements = JavacElements.instance(context);
    }

    public FieldBuilder setType(Class<?> type) {
        this.type = type;

        return this;
    }

    public FieldBuilder setValue(Object value) {
        this.value = value;

        return this;
    }

    public FieldBuilder addAnnotations(Class<?>... annotations) {
        this.annotations.addAll(Arrays.asList(annotations));

        return this;

    }

    public FieldBuilder addModifiers(Modifier... modifiers) {
        this.modifiers.addAll(Arrays.asList(modifiers));

        return this;
    }

    public JCTree.JCVariableDecl build() {
        long flags = this.modifiers.stream()
                .mapToLong(Modifier::getValue)
                .reduce(0, (a, b) -> a | b);

        List<JCTree.JCAnnotation> annotations = List.from(this.annotations.stream()
                .map(a -> treeMaker.Annotation(TypeUtil.getType(a, treeMaker, elements), List.nil()))
                .toArray(JCTree.JCAnnotation[]::new));

        JCTree.JCModifiers modifiers = treeMaker.Modifiers(flags, annotations);

        return treeMaker.VarDef(modifiers, elements.getName(fieldName), TypeUtil.getType(type, treeMaker, elements), treeMaker.Literal(value));
    }
}
