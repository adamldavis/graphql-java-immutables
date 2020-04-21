package com.adamldavis.gji.model

import com.adamldavis.gji.processing.Element

abstract class BaseType {

    abstract String getName()

    List<Property> getProperties() { [] }

    String toString() { getName() }

    static List<Property> properties(final List<Element> elements) {
        elements.collect { element ->
            new Property(Property.createInitializer()
                    .name(element.value)
                    .type(element.attributes.first().value)
                    .nullable(!element.attributes.contains(new Element.Attribute(Element.NON_NULL)))
                    .array(element.attributes.contains(new Element.Attribute(Element.ARRAY))))
        }
    }

}
