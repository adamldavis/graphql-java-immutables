package com.adamldavis.gji.model

import com.adamldavis.gji.processing.api.Element
import groovy.transform.CompileStatic
import groovy.transform.Immutable

@CompileStatic
@Immutable(copyWith = true)
class UnionType extends BaseType {

    String name
    List<Type> types = []

    static UnionType from(final Element element, final Map<String, BaseType> nameToTypeMap) {
        new UnionType(element.attributes[0].value, element.children.collect { (Type) nameToTypeMap[it.value] })
    }

}
