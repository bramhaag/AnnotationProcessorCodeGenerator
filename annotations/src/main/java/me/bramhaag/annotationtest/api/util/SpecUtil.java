package me.bramhaag.annotationtest.api.util;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;
import me.bramhaag.annotationtest.api.ISpec;
import me.bramhaag.annotationtest.api.MethodSpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpecUtil {

    public static void addToClass(Tree tree, Context context, ISpec... specs) {
        if(!(tree instanceof JCTree.JCClassDecl)) {
            throw new IllegalArgumentException("tree is not an instance of JCTree.JCClassDecl");
        }

        JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) tree;
        List<JCTree> trees = new ArrayList<>(classDecl.defs);

        Arrays.stream(specs)
                .map(s -> s.createTree(context, classDecl))
                //.map(t -> getTree(t, classDecl))
                .forEach(trees::add);

        classDecl.defs = com.sun.tools.javac.util.List.from(trees.toArray(new JCTree[0]));
    }

    private static JCTree getTree(JCTree tree, JCTree.JCClassDecl classDecl) {
        if(tree instanceof JCTree.JCMethodDecl) {
            JCTree.JCMethodDecl methodDecl = (JCTree.JCMethodDecl) tree;
            methodDecl.sym = new Symbol.MethodSymbol(methodDecl.mods.flags, methodDecl.name, methodDecl.type, classDecl.sym);
            methodDecl.params.forEach(p -> {
                p.sym = new Symbol.VarSymbol(Flags.PARAMETER, p.name, p.type, methodDecl.sym);
                p.sym.adr = 0;
                p.sym.name = p.name;
            });

            classDecl.sym.members_field.enter(methodDecl.sym);
        }


        return tree;
    }
}
