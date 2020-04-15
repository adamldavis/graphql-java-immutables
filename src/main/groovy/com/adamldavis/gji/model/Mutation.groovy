package com.adamldavis.gji.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class Mutation {

    String name
    String toString() {name}


}
