package com.adamldavis.gji.generation.internal

import com.adamldavis.gji.Config
import com.adamldavis.gji.OutputType
import com.adamldavis.gji.generation.api.CodeGenerator
import com.adamldavis.gji.model.Enumm
import com.adamldavis.gji.model.Root
import com.adamldavis.gji.model.Type
import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

@CompileStatic
@Component
class JavaModelCodeGenerator implements CodeGenerator {

    static final String IMPORT_JACKSON = "import com.fasterxml.jackson.databind.annotation.*;"
    static final String IMPORT_IMMUTABLES = "import org.immutables.value.Value.*;"
    static final String IMPORT_LOMBOK = "import lombok.*;"
    static final String IMPORT_JAVA_TIME = "import java.time.*;"
    static final String IMPORT_JAVA_UTIL = "import java.util.*;"

    @Override
    List<FileOutput> generate(Config config, Root root) {
        final List<FileOutput> fileOutputs = []
        def pack = config.packageName
        def rootDir = new File('src/main/java/' + pack.replace('.', '/'))

        root.types.each { type ->
            final String name = config.classnamePrefix + type.name + config.classnameSuffix
            fileOutputs << new FileOutput(new File(rootDir, "${name}.java"), toCode(config, root, type))
        }
        root.enums.each { enumm ->
            final String name = enumm.name
            fileOutputs << new FileOutput(new File(rootDir, "${name}.java"), toCode(config, enumm))
        }
        fileOutputs
    }

    private static String toCode(Config config, Enumm enumm) {
        final StringBuilder sb = new StringBuilder(config.fileComment)
        sb.append("package ${config.packageName};\n\n")
        sb.append(config.javadocComment)
        sb.append("public enum ${enumm.name} {\n\n")
        enumm.values.each { sb.append('    ').append(it).append(',\n') }
        sb.append('\n}\n')
        sb.toString()
    }

    private static String toCode(Config config, Root root, Type type) {
        final String name = config.classnamePrefix + type.name + config.classnameSuffix
        boolean isLombok = config.outputType == OutputType.LOMBOK_JAVA
        final StringBuilder sb = new StringBuilder(config.fileComment)
        sb.append("package ${config.packageName};\n\n")
        sb.append(IMPORT_JAVA_UTIL).append('\n')
        if (type.properties.any { it.type.toLowerCase().contains('date') || it.type.toLowerCase().contains('time') })
            sb.append(IMPORT_JAVA_TIME).append('\n')

        if (config.includeJacksonJson) sb.append(IMPORT_JACKSON).append('\n')
        if (config.outputType == OutputType.IMMUTABLES)
            sb.append(IMPORT_IMMUTABLES).append('\n').append(config.javadocComment).append('\n@Immutable\n')
        if (isLombok)
            sb.append(IMPORT_LOMBOK).append('\n').append(config.javadocComment).append('\n@Value\n')

        if (config.includeJacksonJson) sb.append("@JsonSerialize\n@JsonDeserialize\n")
        sb.append("public ${isLombok ? 'class' : 'interface'} $name {\n\n")

        type.properties.each { property ->
            def type0 = translateType(config, root, property.type)
            def type1 = property.array ? "List<${type0}>" : type0
            def type2 = property.nullable ? "Optional<${type1}>" : type1
            sb.append('    ').append(isLombok ? 'private ' : 'public ')
                    .append(type2).append(' ')
                    .append(isLombok ? property.name : "get${property.name[0].toUpperCase()}${property.name[1..-1]}")
            sb.append(isLombok ? ';' : '();').append('\n')
        }
        sb.append('\n}\n')
        sb.toString()
    }

    private static String translateType(Config config, Root root, String type) {
        if (root.types.any { it.name == type } ||
                root.inputTypes.any { it.name == type } ||
                root.unionTypes.any { it.name == type }) return config.classnamePrefix + type + config.classnameSuffix
        switch (type) {
            case 'Int': return 'Integer'
            case 'Bool': return 'Boolean'
            case 'ID': return 'String'
            case 'Date': return 'LocalDate'
            case 'Time': return 'LocalTime'
            case 'DateTime': return 'LocalDateTime'
            default: return type
        }
    }
}
