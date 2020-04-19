package com.adamldavis.gji.processing

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

@Canonical
class Element {

    static final String NON_NULL = "_NON_NULL"
    static final String ARRAY = "_ARRAY"

    String value
    final List<Element> children = []
    final List<Attribute> attributes = []

    @EqualsAndHashCode
    @TupleConstructor
    static class Attribute {
        String value
    }

}
