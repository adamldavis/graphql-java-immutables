package com.adamldavis.gji.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class Type {

    String name
    List<Property> properties
    Type parent
    boolean isInputType = false
    boolean isUnionType = false
    boolean isInterface = false
    List<Type> types = []

    String toString() {name}

}
