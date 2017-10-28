package me.bramhaag.annotationtest.processor;

import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import me.bramhaag.annotationtest._old.api.Modifier;
import me.bramhaag.annotationtest._old.api.builders.FieldBuilder;
import me.bramhaag.annotationtest._old.api.builders.MethodBuilder;

import java.util.Arrays;
import java.util.stream.IntStream;

public class TestTreeTranslator extends TreeTranslator {

    private Context context;
    private TreeMaker treeMaker;
    private JavacElements elementUtils;

    public TestTreeTranslator(Context context) {
        this.context = context;
        this.treeMaker = TreeMaker.instance(context);
        this.elementUtils = JavacElements.instance(context);
    }

    @Override
    public <T extends JCTree> T translate(T tree) {
//        if(tree instanceof JCTree.JCMethodDecl) {
//            JCTree.JCMethodDecl method = (JCTree.JCMethodDecl) tree;
//            System.out.println(method.getName().toString());
//
//            if(method.getReturnType() != null)
//            System.out.println(method.getReturnType().getClass().getName());
//
//            if (method.name.toString().equals("main")) {
//                System.out.println("Main method adr:");
//                method.sym.params.forEach(p -> System.out.println(p.type.getClass().getName()));
//            }
//        }

        if(tree instanceof JCTree.JCClassDecl) {
            JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) tree;
            JCTree.JCMethodDecl m = new MethodBuilder("test", context)
                    .setReturnType(String.class)
                    //.addParameter(String.class, "arg")
                    //.addAnnotations(Override.class)
                    //.throwsThrowable(IllegalAccessError.class)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addStatement("System.out.println($L)", "Running test method!")
                    .addStatement("System.out.println($L-$L)", 1L, 10L)
                    .addStatement("return new $L($L)", String.class, "Hello!")
                    .build();

            JCTree.JCVariableDecl f = new FieldBuilder("testField", context)
                    .setType(String.class)
                    .addModifiers(Modifier.PUBLIC)
                    .setValue("this is a test")
                    .build();

            JCTree[] trees = new JCTree[classDecl.defs.length() + 2];
            IntStream.range(0, classDecl.defs.size()).forEach(i -> trees[i] = classDecl.defs.get(i));
            trees[classDecl.defs.length()] = m;
            trees[classDecl.defs.length() + 1] = f;

            System.out.println(Arrays.toString(trees));

            classDecl.defs = List.from(trees);

            //new TreePrinter().visit(classDecl, null);
        }

        return super.translate(tree);
    }
}
