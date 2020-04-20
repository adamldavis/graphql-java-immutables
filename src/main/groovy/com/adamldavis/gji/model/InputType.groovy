package com.adamldavis.gji.model

import com.adamldavis.gji.processing.Element
import groovy.transform.Immutable
import groovy.transform.builder.Builder
import groovy.transform.builder.InitializerStrategy

@Immutable(copyWith = true)
@Builder(builderStrategy = InitializerStrategy)
class InputType extends BaseType {

    String name
    List<Property> properties
    InputType parent

    boolean isInput() {
        true
    }

    static InputType from(final Element element, final Map<String, InputType> nameToTypeMap) {
        new InputType(InputType.createInitializer()
                .name(element.value)
                .properties(properties(element.children))
                .parent(element.attributes.size() == 2 ? nameToTypeMap[element.attributes.last().value] : null))
    }

}
