package com.adamldavis.gji.processing

import groovy.transform.TupleConstructor

@TupleConstructor
class ElementProcessor {

    final Element element

    // name% String %1
    def propertyMissing(String name) {
        element.children << new Element(name)
        this
    }
    // name(param1% String) % Type
    def methodMissing(String name, args) {
        def e = new Element(name)
        element.children.removeAll(args)
        e.children.addAll args
        element.children << e
        this
    }
    /* denotes non-null */
    def mod(Integer num) {
        element.children.last().attributes << new Element.Attribute(Element.NON_NULL)
        this
    }
    /* denotes array */
    def plus(Integer num) {
        element.children.last().attributes << new Element.Attribute(Element.ARRAY)
        this
    }
    // name% String
    def mod(Class clazz) {
        element.children.last().attributes << new Element.Attribute(clazz.simpleName)
        this
    }
    // name% Type
    def mod(ElementProcessor ep) {
        Element last = element.children.last()
        element.children.remove(last)
        element.children.last().attributes << new Element.Attribute(last.value)
        this
    }
}
