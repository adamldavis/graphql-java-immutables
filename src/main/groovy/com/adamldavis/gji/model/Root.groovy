package com.adamldavis.gji.model

import groovy.transform.Immutable
import groovy.transform.builder.Builder
import groovy.transform.builder.InitializerStrategy

@Immutable
@Builder(builderStrategy = InitializerStrategy)
class Root {

    List<Scalar> scalars
    List<Query> queries
    List<Type> types
    List<InterfaceType> interfaceTypes
    List<InputType> inputTypes
    List<UnionType> unionTypes
    List<Mutation> mutations
    List<Enumm> enums

}
