package me.bramhaag.annotationtest.project;

import me.bramhaag.annotationtest.annotations.TestAnnotation;

@TestAnnotation
public class TestClass {

    public TestClass() {
        System.out.println("Hello from constructor 1");
    }

    public TestClass(int x) {
        System.out.println("Hello from constructor 2, x = " + x);
    }

    public static void main(String[] args) {
        //Arrays.stream(TestClass.class.getDeclaredMethods()).map(Method::toString).forEach(System.out::println);

        test();
    }
}
