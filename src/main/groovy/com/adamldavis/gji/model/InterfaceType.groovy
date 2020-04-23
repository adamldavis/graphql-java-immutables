package com.adamldavis.gji.model

import com.adamldavis.gji.processing.api.Element
import groovy.transform.Immutable
import groovy.transform.builder.Builder
import groovy.transform.builder.InitializerStrategy

@Immutable(copyWith = true)
@Builder(builderStrategy = InitializerStrategy)
class InterfaceType extends BaseType {

    String name
    List<Property> properties

    static InterfaceType from(final Element element) {
        new InterfaceType(element.attributes.first().value, properties(element.children))
    }
}
