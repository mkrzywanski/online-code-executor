package io.mkrzywanski.executor.test.data

final class CodeData {
    public static final String HELLO_WORLD_JAVA =
            """public class Test {
                    public static void main(String[] args) {
                        System.out.println(\"hello\");
                    }
               }
            """
    public static final String HELLO_WORLD_GROOVY =
            """class Test {
                    static void main(String[] args) {
                        println \"Hello Groovy\"
                    }
               }
            """
    public static final String KOTLIN_HELLO_WORLD =
            """fun main(args: Array<String>) {
                    println(\"Hello, world!\")
               }
            """
}
