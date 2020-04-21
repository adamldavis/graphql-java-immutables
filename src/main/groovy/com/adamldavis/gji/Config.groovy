package com.adamldavis.gji

import groovy.transform.Canonical

@Canonical
class Config {

    OutputType outputType = OutputType.IMMUTABLES
    String fileComment = ''
    String javadocComment = ''
    boolean includeJacksonJson = true
    String classnameSuffix = 'DTO'
    String classnamePrefix = ''
    String packageName = ''

}
