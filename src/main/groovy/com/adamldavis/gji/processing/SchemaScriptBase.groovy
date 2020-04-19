package com.adamldavis.gji.processing

import com.adamldavis.gji.model.Root

class SchemaScriptBase extends Script {

    final Element root = new Element()

    def getType() {
        new ElementGenerator('type', root)
    }
    def getEnumm() {
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
    def getMutation() {
        new ElementGenerator('mutation', root)
    }
    def getProcess_graph_root() {
        // TODO process the graph into Model
        new Root()
    }

    @Override
    Object run() {
        this
    }
}
