package com.adamldavis.gji.processing.api

import groovy.transform.Canonical
import groovy.transform.Immutable
import groovy.transform.ToString

@ToString(includePackage = false)
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

    @ToString(includePackage = false)
    @Immutable
    static class Attribute {
        String value
    }

}
