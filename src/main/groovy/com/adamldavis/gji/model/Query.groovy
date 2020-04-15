package com.adamldavis.gji.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class Query {

    String name
    String toString() {name}


}
