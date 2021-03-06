/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package com.adamldavis.gji

import com.adamldavis.gji.generation.api.CodeGenerator
import com.adamldavis.gji.model.Root
import com.adamldavis.gji.processing.api.SchemaScriptBase
import org.codehaus.groovy.control.CompilerConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean

import java.util.function.Consumer

@SpringBootApplication
class App {

    @Bean
    Consumer<File> fileConsumer(CodeGenerator codeGenerator, Config config) {
        return { File groovyFile ->
            def compilerConfiguration = new CompilerConfiguration()
            compilerConfiguration.setScriptBaseClass(SchemaScriptBase.class.name)
            GroovyShell shell = new GroovyShell(compilerConfiguration)
            Root root = shell.evaluate(groovyFile)
            codeGenerator.generate(config, root).each { fileOutput ->
                fileOutput.file.parentFile.mkdirs()
                fileOutput.file.text = fileOutput.text
            }
        }
    }

    @ConditionalOnProperty("generator.file")
    @Bean
    File processedFile(Config config, Consumer<File> fileConsumer) {
        final File file = config.file
        println "File=$file"
        File groovyFile = new File(file.name + '.groovy')
        groovyFile.text = toGroovy(file.text)
        fileConsumer.accept(groovyFile)
        return groovyFile
    }

    @ConditionalOnMissingBean(name = "processedFile")
    @Bean
    String missingFile() {
        System.err.println "Missing 'generator.file' property!"
        System.exit(1)
    }

    static void main(String[] args) {
        SpringApplication.run(App, args)
    }

    static String toGroovy(String schema) {
        schema.replace('!', '%1')
                .replace(':', '%')
                .replace('[', '')
                .replace(']', '+1')
                .replace('#', '//')
                .replaceAll('enum +', 'x0.enum.')
                .replaceAll('scalar +', 'x0.scalar.')
                .replaceAll('type +', 'x0.type.')
                .replaceAll('interface +', 'x0.interface.')
                .replaceAll(' +implements +', '.implements_') //x0.type.Foo.implements_Bar {
                .replaceAll('union +', 'x0.union.')
                .replaceAll('mutation +', 'x0.mutation.') + '\nprocess_graph_root'
    }
}
