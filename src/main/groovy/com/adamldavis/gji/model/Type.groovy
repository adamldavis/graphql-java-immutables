package com.adamldavis.gji.model

import com.adamldavis.gji.processing.Element
import groovy.transform.Immutable
import groovy.transform.builder.Builder
import groovy.transform.builder.InitializerStrategy

@Immutable(copyWith = true)
@Builder(builderStrategy = InitializerStrategy)
class Type extends BaseType {

    String name
    List<Property> properties
    Type parent

    static Type from(final Element element, final Map<String, Type> nameToTypeMap) {
        new Type(Type.createInitializer()
                .name(element.value)
                .properties(properties(element.children))
                .parent(element.attributes.size() == 2 ? nameToTypeMap[element.attributes.last().value] : null))
    }

}
