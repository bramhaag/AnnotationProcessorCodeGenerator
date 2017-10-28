package me.bramhaag.annotationtest.api.util;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;
import me.bramhaag.annotationtest.api.ISpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpecUtil {

    public static void addToClass(Tree tree, Context context, ISpec... spec) {
        if(!(tree instanceof JCTree.JCClassDecl)) {
            throw new IllegalArgumentException("tree is not an instance of JCTree.JCClassDecl");
        }

        JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) tree;
        List<JCTree> trees = new ArrayList<>(classDecl.defs);

        Arrays.stream(spec)
                .map(s -> s.createTree(context))
                .forEach(trees::add);

        classDecl.defs = com.sun.tools.javac.util.List.from(trees.toArray(new JCTree[0]));
    }
}
