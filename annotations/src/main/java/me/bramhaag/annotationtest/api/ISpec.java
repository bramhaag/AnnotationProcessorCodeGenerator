package me.bramhaag.annotationtest.api;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;

public interface ISpec {

//    private String name;
//
//    private Modifier[] modifiers;
//    private Class<?>[] annotations;
//
//    public ISpec(String name, Modifier[] modifiers, Class<?>[] annotations) {
//        this.name = name;
//        this.modifiers = modifiers;
//        this.annotations = annotations;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Modifier[] getModifiers() {
//        return modifiers;
//    }
//
//    public Class<?>[] getAnnotations() {
//        return annotations;
//    }

    JCTree createTree(Context context);
}
