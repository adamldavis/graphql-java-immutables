# graphql-java-immutables

Generator for Immutable Java classes from GraphQL Schema.

This project assists you in creating either a GraphQL server 
or client in Java. You can configure it to create either 
[Lombok](https://projectlombok.org/) or [Immutables](https://immutables.github.io/)
based DTOs. 

## Getting Started

Clone the project and run `./gradlew build`

This should build the project using Gradle.

Then run:

```text
java -jar build/libs/graphql-java-immutables-0.0.1.jar
```

You get a warning like `Missing 'generator.file' property!`

Set it like this:

```text
export GENERATOR_FILE=your_file.graphql
```

Where `your_file.graphql` points to a valid GraphQL schema.

## Config

All the available configuration properties and the defaults are the following:

```groovy
    File file
    OutputType outputType = IMMUTABLES or LOMBOK
    String fileComment = ''
    String javadocComment = ''
    boolean includeJacksonJson = true
    String classnameSuffix = 'DTO'
    String classnamePrefix = ''
    String packageName = 'org.example'
```

Each of these should be set using environment variable.
For example, to set the package name:

```text
export GENERATOR_PACKAGENAME=com.your_package
```

## Output

Files are put under the `src/main/java` directory.

## Examples

For example, given the following GraphQL file:

```
scalar Date
scalar DateTime

type Query {
  user(name: String): User
}

type User {
  created: Date!
  id: ID!
  name: String!
  type: UserType!
  events: [Event!]
}

type Event {
  when: DateTime!
  type: String!
  comments: String
}

enum UserType {
    USER
    ADMIN
}
```

It will create a User class, Event class, and UserType enum.
