package com.adamldavis.gji.generation.api

import com.adamldavis.gji.Config
import com.adamldavis.gji.model.Root
import groovy.transform.Immutable

interface CodeGenerator {

    @Immutable(knownImmutableClasses = [File.class])
    class FileOutput {
        File file
        String text
    }

    List<FileOutput> generate(Config config, Root root)

}
