package com.adamldavis.gji.model

import groovy.transform.Immutable

@Immutable
class Scalar {

    String name
    String toString() {name}

}
