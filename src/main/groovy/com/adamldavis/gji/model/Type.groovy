package com.adamldavis.gji.model

import com.adamldavis.gji.processing.Element
import groovy.transform.CompileStatic
import groovy.transform.Immutable

@CompileStatic
@Immutable(copyWith = true)
class Type extends BaseType {

    String name
    List<Property> properties
    Type parent

    static Type from(final Element element, final Map<String, Type> nameToTypeMap) {
        new Type(element.attributes[0].value,
                properties(element.children),
                (element.attributes.size() == 2) ? nameToTypeMap[element.attributes.last().value] : null)
    }

}
