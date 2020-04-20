package com.adamldavis.gji.model

import com.adamldavis.gji.processing.Element
import groovy.transform.Immutable

@Immutable(copyWith = true)
class Mutation {

    String name
    String toString() {name}

    static Mutation from(final Element element) {
        new Mutation(element.value, Type.properties(element.children))
    }

}
