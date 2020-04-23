package com.adamldavis.gji.generation.internal

import com.adamldavis.gji.Config
import com.adamldavis.gji.OutputType
import com.adamldavis.gji.generation.api.CodeGenerator
import com.adamldavis.gji.model.Enumm
import com.adamldavis.gji.model.Root
import com.adamldavis.gji.model.Type
import org.springframework.stereotype.Component

@Component
class JavaModelCodeGenerator implements CodeGenerator {

    static final String IMPORT_JACKSON = "import com.fasterxml.jackson.databind.annotation.*;"
    static final String IMPORT_IMMUTABLES = "import org.immutables.value.Value.*;"
    static final String IMPORT_LOMBOK = "import lombok.*;"

    @Override
    void gen(Config config, Root root) {
        def pack = config.packageName
        def rootDir = new File('src/main/java/' + pack.replace('.', '/'))
        rootDir.mkdirs()
        root.types.each { type ->
            final String name = config.classnamePrefix + type.name + config.classnameSuffix
            new File(rootDir, "${name}.java").text = toCode(config, type)
        }
        root.enums.each { enumm ->
            final String name = enumm.name
            new File(rootDir, "${name}.java").text = toCode(config, enumm)
        }
    }

    private String toCode(Config config, Enumm enumm) {
        final StringBuilder sb = new StringBuilder(config.fileComment)
        sb.append("package ${config.packageName};\n\n")
        sb.append(config.javadocComment)
        sb.append("public enum ${enumm.name} {\n\n")
        enumm.values.each { sb.append('    ').append(it).append(',\n') }
        sb.append('\n}\n')
        sb.toString()
    }

    private String toCode(Config config, Type type) {
        final String name = config.classnamePrefix + type.name + config.classnameSuffix
        boolean isLombok = config.outputType == OutputType.LOMBOK_JAVA
        final StringBuilder sb = new StringBuilder(config.fileComment)
        sb.append("package ${config.packageName};\n\n")
        if (config.includeJacksonJson) sb.append(IMPORT_JACKSON).append('\n')
        if (config.outputType == OutputType.IMMUTABLES) sb.append(IMPORT_IMMUTABLES).append('\n@Immutable\n')
        if (isLombok) sb.append(IMPORT_LOMBOK).append('\n@Data\n')

        sb.append(config.javadocComment)
        sb.append("public ${isLombok ? 'class' : 'interface'} $name {\n\n")

        type.properties.each { property ->
            def type1 = property.array ? "List<${property.type}>" : property.type
            sb.append('    ').append(isLombok ? 'private ' : 'public ')
                    .append(type1).append(' ')
                    .append(isLombok ? property.name : "get${property.name[0..1].toUpperCase()}${property.name[1..-1]}")
            sb.append(isLombok ? ';' : '();').append('\n')
        }
        sb.append('\n}\n')
        sb.toString()
    }
}
