package com.adamldavis.gji.processing

import groovy.transform.Canonical

@Canonical
class Element {

    static final String NON_NULL = "_NON_NULL"
    static final String ARRAY = "_ARRAY"

    String value
    final List<Element> children = []
    final List<Attribute> attributes = []

    Element or(final Element other) {
        children << other
        this
    }

    @Canonical
    static class Attribute {
        String value
    }

}
