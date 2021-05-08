package io.mkrzywanski.executor.test.data

final class CompiledClassesObjectMother {

    private CompiledClassesObjectMother() {
    }

    static <T> T helloWorld(final ClassInfoToObjectMapper<T> mapper) {
        final Class<?> c = HelloWorld.class
        final String className = c.getName()
        final String classAsPath = className.replace('.', '/') + ".class"
        final InputStream stream = c.getClassLoader().getResourceAsStream(classAsPath)
        try {
            final byte[] bytes = Objects.requireNonNull(stream).readAllBytes()
            return mapper.apply(HelloWorld.class.getName(), bytes)
        } catch (final IOException e) {
            throw new IllegalStateException(e)
        }
    }

    static String helloWorldJava() {
        return """public class Test {
                                public static void main(String[] args) {
                                    System.out.println(\"hello\");
                                }
                            }"""
    }

}
