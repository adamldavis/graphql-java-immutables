package com.adamldavis.gji.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class Enumm {

    String name
    List<String> values

    String toString() {name}

}
