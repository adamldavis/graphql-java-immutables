package com.adamldavis.gji.model

import com.adamldavis.gji.processing.api.Element
import groovy.transform.Immutable

@Immutable(copyWith = true)
class Query extends BaseType {

    String name
    List<Property> properties

    String toString() { name }

    static Query from(final Element element) {
        new Query(element.value, properties(element.children))
    }

}
