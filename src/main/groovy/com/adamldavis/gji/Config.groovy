package com.adamldavis.gji

import groovy.transform.Canonical
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "generator")
@Canonical
class Config {

    File file
    OutputType outputType = OutputType.IMMUTABLES
    String fileComment = ''
    String javadocComment = ''
    boolean includeJacksonJson = true
    String classnameSuffix = 'DTO'
    String classnamePrefix = ''
    String packageName = 'org.example'

}
