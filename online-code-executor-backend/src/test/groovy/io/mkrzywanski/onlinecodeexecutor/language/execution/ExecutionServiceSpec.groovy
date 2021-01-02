//package io.mkrzywanski.onlinecodeexecutor.language.execution
//
//
//import io.mkrzywanski.onlinecodeexecutor.language.Code
//import io.mkrzywanski.onlinecodeexecutor.language.Language
//import io.mkrzywanski.onlinecodeexecutor.language.LanguageTools
//import io.mkrzywanski.onlinecodeexecutor.language.LanguageToolsResolver
//import io.mkrzywanski.onlinecodeexecutor.language.compilation.Compiler
//import io.mkrzywanski.onlinecodeexecutor.language.loading.ClassLoadingService
//import spock.lang.Specification
//
//class ExecutionServiceSpec extends Specification {
//
//    def executor = Mock(Executor)
//    def compiler = Mock(Compiler)
//    def languageTools = Mock(LanguageTools)
//    def languageToolsResolver = Mock(LanguageToolsResolver)
//    def classLoadingService = Mock(ClassLoadingService)
//    def exe = new ExecutionService(languageToolsResolver, classLoadingService)
//
//    def "should return code execution result"() {
//        given:
//        def codeString = ""
//        def code = new Code(Language.JAVA, )
//        languageToolsResolver.resolve(Language.JAVA) >> Optional.of(languageTools)
//
//        when:
//        exe.execute()
//    }
//}
