package com.adamldavis.gji.processing.internal

import com.adamldavis.gji.processing.api.Element
import groovy.transform.TupleConstructor

@TupleConstructor
class SchemaBuilder {

    final Element root

    def getType() {
        new ElementGenerator('type', root)
    }

    def getInterface() {
        new ElementGenerator('interface', root)
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
}
