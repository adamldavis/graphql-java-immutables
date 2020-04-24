package com.adamldavis.gji

import com.adamldavis.gji.generation.internal.JavaModelCodeGenerator
import com.adamldavis.gji.model.Enumm
import com.adamldavis.gji.model.Property
import com.adamldavis.gji.model.Root
import com.adamldavis.gji.model.Type
import spock.lang.Specification

class GeneratorSpec extends Specification {

    def "should generate Java file for Type"() {
        setup:
        def generator = new JavaModelCodeGenerator()
        def config = new Config(fileComment: '/** test */\n', javadocComment: '/** Javadoc */')
        when:
        def root = new Root(Root.createInitializer()
                .types([new Type('Foo', [new Property('name', 'Int', false, false)], null)])
                .unionTypes([])
                .enums([])
                .interfaceTypes([])
                .inputTypes([])
                .queries([])
                .mutations([])
                .scalars([])
        )
        def results = generator.generate(config, root)
        then:
        results.size() == 1
        results.first().file.name == 'FooDTO.java'
        results.first().text == '''/** test */
package org.example;

import java.util.*;
import com.fasterxml.jackson.databind.annotation.*;
import org.immutables.value.Value.*;
/** Javadoc */
@Immutable
@JsonSerialize
@JsonDeserialize
public interface FooDTO {

    public Integer getName();

}
'''
    }

    def "should generate Java file for Enum"() {
        setup:
        def generator = new JavaModelCodeGenerator()
        def config = new Config()
        when:
        def root = new Root(Root.createInitializer()
                .types([])
                .unionTypes([])
                .enums([new Enumm("Animal", ['DOG', 'CAT'])])
                .interfaceTypes([])
                .inputTypes([])
                .queries([])
                .mutations([])
                .scalars([])
        )
        def results = generator.generate(config, root)
        then:
        results.size() == 1
        results.first().file.name == 'Animal.java'
        results.first().text == '''package org.example;

public enum Animal {

    DOG,
    CAT,

}
'''
    }

}
