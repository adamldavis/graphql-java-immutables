package com.adamldavis.gji.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class Enumm extends BaseType {

    String name
    List<String> values

}
