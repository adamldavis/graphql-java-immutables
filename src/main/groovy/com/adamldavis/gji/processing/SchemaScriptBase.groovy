package com.adamldavis.gji.processing

import com.adamldavis.gji.model.BaseType
import com.adamldavis.gji.model.Enumm
import com.adamldavis.gji.model.InputType
import com.adamldavis.gji.model.InterfaceType
import com.adamldavis.gji.model.Mutation
import com.adamldavis.gji.model.Query
import com.adamldavis.gji.model.Root
import com.adamldavis.gji.model.Scalar
import com.adamldavis.gji.model.Type
import com.adamldavis.gji.model.UnionType
import groovy.transform.CompileStatic

@CompileStatic
class SchemaScriptBase extends Script {

    final Element root = new Element()

    SchemaScriptBase getX0() {
        this
    }

    def getType() {
        new ElementGenerator('type', root)
    }

    def getInterface() {
        new ElementGenerator('interface', root)
    }

    def getImplements() {
        new ElementGenerator('implements', root)
    }

    def getEnum() {
        new ElementGenerator('enum', root)
    }

    def getScalar() {
        new ElementGenerator('scalar', root)
    }

    def getUnion() {
        new ElementGenerator('union', root)
    }

    def getInput() {
        new ElementGenerator('input', root)
    }

    def getQuery() {
        new ElementGenerator('query', root)
    }

    def getMutation() {
        new ElementGenerator('mutation', root)
    }

    def getProcess_graph_root() {
        final Map<String, BaseType> nameToTypeMap = [:]
        def scalars = root.children.findAll { it.value == 'scalar' }.collect { element ->
            new Scalar(element.attributes[0].value)
        }
        def enumms = root.children.findAll { it.value == 'enum' }.collect { element ->
            new Enumm(element.attributes[0].value, element.children.collect { it.value })
        }
        def interfaces = root.children.findAll { it.value == 'interface' }.collect { element ->
            InterfaceType typeInterface = InterfaceType.from(element)
            nameToTypeMap[typeInterface.name] = typeInterface
            typeInterface
        }
        def types = root.children.findAll { it.value == 'type' }.collect { element ->
            Type type = Type.from(element, nameToTypeMap)
            nameToTypeMap[type.name] = type
            type
        }
        def unions = root.children.findAll { it.value == 'union' }.collect { element ->
            UnionType.from(element, nameToTypeMap)
        }
        def inputs = root.children.findAll { it.value == 'input' }.collect { element ->
            InputType.from(element, nameToTypeMap)
        }
        new Root(Root.createInitializer()
                .scalars(scalars)
                .enums(enumms)
                .types(types)
                .inputTypes(inputs)
                .interfaceTypes(interfaces)
                .unionTypes(unions)
                .queries(root.children.findAll { it.value == 'query' }.collect { element ->
                    Query.from(element)
                })
                .mutations(root.children.findAll { it.value == 'mutation' }.collect { element ->
                    Mutation.from(element)
                }))
    }

    @Override
    Object run() {
        this
    }
}
