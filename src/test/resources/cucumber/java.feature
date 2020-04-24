Feature: Type processing to Java
  Type should be processed properly into Java code

  Scenario Outline: Types are processed into corresponding Java Immutables code
    Given Input is <input>
    When I convert to "Immutables"
    Then The code output should contain <output1>
    And The code output should contain <output2>

    Examples:
      | input                                           | output1                                | output2                                     |
      | "type Foo { name: String! }"                    | "@Immutable\public interface FooDTO {" | "public String getName();"                  |
      | "type Foo { age: Int! }"                        | "@Immutable\public interface FooDTO {" | "public Integer getAge();"                  |
      | "type Foo { yes: Bool! }"                       | "@Immutable\public interface FooDTO {" | "public Boolean getYes();"                  |
      | "type Bar { name: String!\n version: String! }" | "public String getName();"             | "public String getVersion();"               |
      | "type Foo { name: String }"                     | "@Immutable\public interface FooDTO {" | "public Optional<String> getName();"        |
      | "type Foo { ages: [Int] }"                      | "import java.util.*;"                  | "public Optional<List<Integer>> getAges();" |
      | "type Foo { names:[String]! }"                  | "@Immutable\public interface FooDTO {" | "public List<String> getNames();"           |
      | "type Foo { date:Date! }"                       | "import java.time.*;"                  | "public Date getDate();"                    |
