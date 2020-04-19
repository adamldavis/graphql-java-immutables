package com.adamldavis.gji.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class Property {

    String name
    Type type

}
