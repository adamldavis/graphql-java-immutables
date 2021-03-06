package com.adamldavis.gji.model

import groovy.transform.Immutable
import groovy.transform.builder.Builder
import groovy.transform.builder.InitializerStrategy

@Immutable(copyWith = true)
@Builder(builderStrategy = InitializerStrategy)
class Property {

    String name
    String type
    boolean nullable
    boolean array

}
