package com.adamldavis.gji.model

import com.adamldavis.gji.processing.api.Element
import groovy.transform.CompileStatic
import groovy.transform.Immutable

@CompileStatic
@Immutable(copyWith = true)
class Type extends BaseType {

    String name
    List<Property> properties
    InterfaceType parent

    static Type from(final Element element, final Map<String, BaseType> nameToTypeMap) {
        new Type(element.attributes[0].value,
                properties(element.children),
                (InterfaceType) ((element.attributes.size() == 2) ? nameToTypeMap[element.attributes.last().value] : null))
    }

}
