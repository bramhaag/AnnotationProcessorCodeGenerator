package me.bramhaag.annotationtest.api.util;

import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;

public class TypeUtil {
    public static JCTree.JCExpression getType(Class<?> clazz, TreeMaker treeMaker, JavacElements elements) {
        JCTree.JCExpression expr;

        if(clazz.isArray()) {
            int dims = 0;
            while (clazz.isArray()) {
                clazz = clazz.getComponentType();
                dims++;
            }

            expr = getTypeClass(clazz, treeMaker, elements);

            for(int i = 0; i < dims; i++) {
                expr = treeMaker.TypeArray(expr);
            }
        } else {
            expr = getTypeClass(clazz, treeMaker, elements);
        }

        return expr;
    }

    private static JCTree.JCExpression getTypeClass(Class<?> clazz, TreeMaker treeMaker, JavacElements elements) {
        if(clazz.isPrimitive()) {
            //TODO
            return null;
        }

        String[] arr = clazz.getName().split("\\.");
        JCTree.JCExpression expr = treeMaker.Ident(elements.getName(arr[0]));

        for (int i = 1; i < arr.length; i++) {
            expr = treeMaker.Select(expr, elements.getName(arr[i]));
        }

        return expr;
    }
}
