package com.adamldavis.gji

import com.adamldavis.gji.model.Property
import com.adamldavis.gji.model.Root
import com.adamldavis.gji.processing.api.Element
import com.adamldavis.gji.processing.api.SchemaScriptBase
import spock.lang.Specification

class ProcessingSpec extends Specification {

    def "should convert from Groovy"() {
        setup:
        def base = new SchemaScriptBase()
        when:
        def result = base.x0.type.Groovy { versions% String%1+1 }
        then:
        result instanceof Element
        def element = (Element) result
        element.children.size() == 1
        element.value == 'type'
        element.attributes == [new Element.Attribute('Groovy')]
        element.children.first().value == 'versions'
        element.children.first().attributes == [new Element.Attribute('String'),
                                                new Element.Attribute(Element.NON_NULL), new Element.Attribute(Element.ARRAY)]
    }

    def "should process elements into Root"() {
        setup:
        def base = new SchemaScriptBase()
        when:
        base.x0.type.Groovy { versions% String%1+1 }
        def result = base.getProcess_graph_root()
        then:
        result instanceof Root
        result.types.size() == 1
        result.types.first().name == 'Groovy'
        result.types.first().properties == [new Property('versions', 'String', false, true)]
    }

    def "should process scalars"() {
        setup:
        def base = new SchemaScriptBase()
        when:
        base.x0.scalar.Foo
        base.x0.scalar.Bar
        def result = base.getProcess_graph_root()
        then:
        result instanceof Root
        result.scalars.size() == 2
        result.scalars.first().name == 'Foo'
        result.scalars.last().name == 'Bar'
    }

    def "should process enums"() {
        setup:
        def base = new SchemaScriptBase()
        when:
        base.x0.enum.Foo { 
            F1
            F2
        }
        base.x0.enum.Bar {
            B1
            B2
        }
        def result = base.getProcess_graph_root()
        then:
        result instanceof Root
        result.enums.size() == 2
        result.enums.first().name == 'Foo'
        result.enums.first().values == ['F1', 'F2']
        result.enums.last().name == 'Bar'
        result.enums.last().values == ['B1', 'B2']
    }

    def "should process interfaces"() {
        setup:
        def base = new SchemaScriptBase()
        when:
        base.x0.interface.Language { versions% String%1+1 }
        base.x0.type.Groovy.implements_Language { }
        def result = base.getProcess_graph_root()
        then:
        result instanceof Root
        result.types.size() == 1
        result.types.first().name == 'Groovy'
        result.types.first().parent.name == 'Language'
    }

    def "should process unions"() {
        setup:
        def base = new SchemaScriptBase()
        when:
        base.x0.type.Foo { value% String }
        base.x0.type.Bar { value2% String }
        base.x0.type.Baz { value3% String }
        base.x0.union.Foobar = base.Foo | base.Bar | base.Baz
        def result = base.getProcess_graph_root()
        then:
        result instanceof Root
        result.types.size() == 3
        result.unionTypes.size() == 1
        result.unionTypes.first().name == 'Foobar'
        result.unionTypes.first().types.toSet() == result.types.toSet()
    }

}
