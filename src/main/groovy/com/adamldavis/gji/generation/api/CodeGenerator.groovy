package com.adamldavis.gji.generation.api

import com.adamldavis.gji.Config
import com.adamldavis.gji.model.Root

interface CodeGenerator {

    void gen(Config config, Root root)

}
