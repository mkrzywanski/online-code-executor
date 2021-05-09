package io.mkrzywanski.executor.test.data

final class CodeData {

    static class Java {
        public static final String HELLO_WORLD =
                """
                    public class Test {
                        public static void main(String[] args) {
                            System.out.println(\"hello\");
                        }
                }
                """
        public static final String EMPTY_CLASS =
                """
                    public class Test {
                    }
                """
        public static final String NESTED_CLASS =
                """
                    public class Test {
                        public static void main(String[] args) {
                            System.out.println(\"HelloWorld\");
                        }
                        public class NestedClass {
                        
                        }
                    }
                """
    }

    static class Groovy {

        public static final String HELLO_WORLD =
                """class Test {
                    static void main(String[] args) {
                        println \"Hello Groovy\"
                    }
               }
            """
    }

    static class Kotlin {
        public static final String HELLO_WORLD =
                """fun main(args: Array<String>) {
                    println(\"Hello, world!\")
               }
            """

    }
}