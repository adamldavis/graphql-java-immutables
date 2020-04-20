package com.adamldavis.gji.model

import com.adamldavis.gji.processing.Element
import groovy.transform.Immutable
import groovy.transform.builder.Builder
import groovy.transform.builder.InitializerStrategy


@Immutable(copyWith = true)
@Builder(builderStrategy = InitializerStrategy)
class UnionType extends BaseType {

    String name
    List<Type> types = []

    static UnionType from(final Element element, final Map<String, Type> nameToTypeMap) {
        new UnionType(UnionType.createInitializer()
                .name(element.value)
                .types(element.children.collect { nameToTypeMap[it.value] }))
    }

}
