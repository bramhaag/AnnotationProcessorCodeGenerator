package me.bramhaag.annotationtest.printer;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.tree.JCTree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TreePrinter extends TreeVisitor8<Void, Void> {

    private static Map<Class, Function> printer = new HashMap<>();

    static {
        put(JCTree.JCIdent.class, tree -> "Ident[" + tree.getName().toString() + "]");
        put(JCTree.JCFieldAccess.class, tree -> "FieldAccess[" + tree.getIdentifier() + "]");
        put(JCTree.JCLiteral.class, tree -> "Literal[" + tree.getValue().toString() + "]");
        put(JCTree.JCMethodDecl.class, tree -> "Method[" + tree.getName().toString() + "]");
        put(JCTree.JCModifiers.class, tree -> "Modifiers[" + tree.getFlags().stream().map(Enum::name).collect(Collectors.joining("|")) + "]");
        put(JCTree.JCVariableDecl.class, tree -> "VariableDecl[" + tree.getName() + "]");
        put(JCTree.JCBlock.class, tree -> "Block[" + tree.flags + "]");
        put(JCTree.JCTypeParameter.class, tree -> "TypeParameter[" + tree.getName() + "]");
    }

    private int depth;

    private static <T extends Tree> void put(Class<T> cls, Function<T, String> func) {
        printer.put(cls, func);
    }

    public final Void visit(Tree var1, Void var2) {
        if (var1 == null) return null;
        print(var1);
        depth++;
        Void v = var1.accept(this, var2);
        depth--;
        return v;
    }

    public final Void visit(Iterable<? extends Tree> var1, Void var2) {
        Void var3 = null;
        Tree var5;
        if (var1 != null) {
            for (Iterator var4 = var1.iterator(); var4.hasNext(); var3 = this.visit(var5, var2)) {
                var5 = (Tree) var4.next();
            }
        }

        return var3;
    }

    private void print(Tree tree) {
        StringBuilder builder = new StringBuilder(depth);
        for (int i = 0; i < depth; i++) builder.append(' ');
        String node = (String) printer.getOrDefault(tree.getClass(), t -> t.getClass().getSimpleName()).apply(tree);
        System.out.println(builder.append(node));
    }
}