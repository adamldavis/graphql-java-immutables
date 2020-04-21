package com.adamldavis.gji.model

import com.adamldavis.gji.processing.Element
import groovy.transform.Immutable

@Immutable(copyWith = true)
class InputType extends BaseType {

    String name
    List<Property> properties
    InputType parent

    boolean isInput() {
        true
    }

    static InputType from(final Element element, final Map<String, InputType> nameToTypeMap) {
        new InputType(element.attributes[0].value, properties(element.children),
                (element.attributes.size() == 2) ? nameToTypeMap[element.attributes.last().value] : null)
    }

}
