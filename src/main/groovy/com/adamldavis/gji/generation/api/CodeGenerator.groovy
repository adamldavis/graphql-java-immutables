package com.adamldavis.gji.generation.api

import com.adamldavis.gji.Config
import com.adamldavis.gji.model.Root
import groovy.transform.Canonical

interface CodeGenerator {

    @Canonical
    class FileOutput {
        File file
        String text
    }

    List<FileOutput> generate(Config config, Root root)

}
