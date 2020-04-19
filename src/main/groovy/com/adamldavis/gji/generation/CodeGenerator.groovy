package com.adamldavis.gji.generation

import com.adamldavis.gji.Config
import com.adamldavis.gji.model.Root

interface CodeGenerator {

    void gen(Config config, Root root)

}
