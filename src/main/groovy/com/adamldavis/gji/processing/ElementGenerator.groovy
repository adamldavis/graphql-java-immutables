package com.adamldavis.gji.processing

class ElementGenerator {

    String type
    Element root
    Element element

    ElementGenerator(String type, Element root) {
        this.type = type
        this.root = root
        element = new Element(type)
        root.children.add(element)
    }

    def methodMissing(String name, args) {
        //type.User { name% String %1 }
        // type == element, 'User' == element.text, children == [name]
        if (args.length == 1 && args[0] instanceof Closure) {
            __generate(name, (Closure) args[0])
        } else if (args.length == 0) {
            element.attributes << new Element.Attribute(name)
        }
        element
    }

    def __generate(String name, Closure closure) {
        element.attributes << new Element.Attribute(name)
        closure.delegate = new ElementProcessor(element)
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
    }

}
