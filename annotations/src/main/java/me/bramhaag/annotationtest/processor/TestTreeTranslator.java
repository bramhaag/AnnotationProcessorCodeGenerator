package me.bramhaag.annotationtest.processor;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import me.bramhaag.annotationtest.api.FieldSpec;
import me.bramhaag.annotationtest.api.MethodSpec;
import me.bramhaag.annotationtest.api.util.Modifier;
import me.bramhaag.annotationtest.api.util.SpecUtil;

public class TestTreeTranslator extends TreeTranslator {

    private Context context;

    public TestTreeTranslator(Context context) {
        this.context = context;
    }

    @Override
    public <T extends JCTree> T translate(T tree) {
        if(tree instanceof JCTree.JCClassDecl) {
//            JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) tree;
//            JCTree.JCMethodDecl m = new MethodBuilder("test", context)
//                    .setReturnType(String.class)
//                    //.addParameter(String.class, "arg")
//                    //.addAnnotations(Override.class)
//                    //.throwsThrowable(IllegalAccessError.class)
//                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                    .addStatement("System.out.println($L)", "Running test method!")
//                    .addStatement("System.out.println($L-$L)", 1L, 10L)
//                    .addStatement("return new $L($L)", String.class, "Hello!")
//                    .build();
//
//            JCTree.JCVariableDecl f = new FieldBuilder("testField", context)
//                    .setType(String.class)
//                    .addModifiers(Modifier.PUBLIC)
//                    .setValue("this is a test")
//                    .build();
//
//            JCTree[] trees = new JCTree[classDecl.defs.length() + 2];
//            IntStream.range(0, classDecl.defs.size()).forEach(i -> trees[i] = classDecl.defs.get(i));
//            trees[classDecl.defs.length()] = m;
//            trees[classDecl.defs.length() + 1] = f;
//
//            System.out.println(Arrays.toString(trees));
//
//            classDecl.defs = List.from(trees);

            //new TreePrinter().visit(classDecl, null);

            MethodSpec method = new MethodSpec.Builder("test")
                    .setReturnType(String.class)
                    .addParameter(String.class, "myParam")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addStatement("System.out.println($L)", "Running test method!")
                    .addStatement("System.out.println($L-$L)", 1L, 10L)
                    .addStatement("return new $L($L)", String.class, "Hello!")
                    .build();

//            FieldSpec field = new FieldSpec.Builder(String.class,"testField")
//                    .addModifiers(Modifier.PUBLIC)
//                    .setValue("this is a test")
//                    .build();

            SpecUtil.addToClass(tree, context, method);

            System.out.println(((JCTree.JCClassDecl) tree).defs);
        }

        return super.translate(tree);
    }
}
