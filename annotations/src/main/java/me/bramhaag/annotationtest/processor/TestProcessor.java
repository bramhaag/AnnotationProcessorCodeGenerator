package me.bramhaag.annotationtest.processor;

import com.google.auto.service.AutoService;
import com.sun.source.util.Trees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import me.bramhaag.annotationtest.annotations.TestAnnotation;
import me.bramhaag.annotationtest.printer.TreePrinter;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("me.bramhaag.annotationtest.annotations.TestAnnotation")
@AutoService(Processor.class)
public class TestProcessor extends AbstractProcessor {

    private JavacProcessingEnvironment env;
    private Messager messager;

    @Override
    public void init(ProcessingEnvironment env) {
        this.env = (JavacProcessingEnvironment) env;
        this.messager = env.getMessager();

        messager.printMessage(Diagnostic.Kind.NOTE, "Starting...");

        super.init(env);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Trees trees = Trees.instance(env);

        for(Element e : roundEnv.getElementsAnnotatedWith(TestAnnotation.class)) {
            messager.printMessage(Diagnostic.Kind.NOTE, "Found element: " + e.getSimpleName());

            if(e.getKind() != ElementKind.CLASS) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Cannot use annotation on anything other than a class!");
                return false;
            }

            new TreePrinter().visit(trees.getPath(e).getCompilationUnit(), null);
            new TestTreeTranslator(env.getContext()).translate((JCTree) trees.getPath(e).getCompilationUnit());
        }

        return false;
    }
}
