package com.adamldavis.gji.model

import com.adamldavis.gji.processing.Element
import groovy.transform.Immutable

@Immutable(copyWith = true)
class UnionType extends BaseType {

    String name
    List<Type> types = []

    static UnionType from(final Element element, final Map<String, Type> nameToTypeMap) {
        new UnionType(element.attributes[0].value, element.children.collect { nameToTypeMap[it.value] })
    }

}
