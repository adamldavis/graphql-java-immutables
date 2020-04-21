package com.adamldavis.gji

import com.adamldavis.gji.model.Property
import com.adamldavis.gji.model.Root
import com.adamldavis.gji.processing.Element
import com.adamldavis.gji.processing.SchemaScriptBase
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

}
