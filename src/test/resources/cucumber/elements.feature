Feature: Type processing
  Type should be processed properly with multiple fields

  Scenario Outline: Type is processed into Elements
    Given Input is <input>
    When I parse the input
    Then The element output should be <output>

    Examples:
      | input                                         | output                                                                                                                                                              |
      | "type Foo { name: String }"                   | "Element(null, [Element(type, [Element(name, [], [Element$Attribute(String)])], [Element$Attribute(Foo)])], [])"                                                    |
      | "type Foo { age: Int }"                       | "Element(null, [Element(type, [Element(age, [], [Element$Attribute(Int)])], [Element$Attribute(Foo)])], [])"                                                        |
      | "type Foo { yes: Bool }"                      | "Element(null, [Element(type, [Element(yes, [], [Element$Attribute(Bool)])], [Element$Attribute(Foo)])], [])"                                                       |
      | "type Bar { name: String\n version: String }" | "Element(null, [Element(type, [Element(name, [], [Element$Attribute(String)]), Element(version, [], [Element$Attribute(String)])], [Element$Attribute(Bar)])], [])" |
