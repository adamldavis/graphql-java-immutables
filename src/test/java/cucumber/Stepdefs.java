package cucumber;

import com.adamldavis.gji.App;
import com.adamldavis.gji.Config;
import com.adamldavis.gji.OutputType;
import com.adamldavis.gji.generation.api.CodeGenerator;
import com.adamldavis.gji.generation.internal.JavaModelCodeGenerator;
import com.adamldavis.gji.model.Root;
import com.adamldavis.gji.processing.api.Element;
import com.adamldavis.gji.processing.api.SchemaScriptBase;
import groovy.lang.GroovyShell;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class Stepdefs {

    final CodeGenerator codeGenerator = new JavaModelCodeGenerator();
    String input;
    Element rootElement;
    String output;
    boolean includeJson = false;
    String suffix = "";
    final List<CodeGenerator.FileOutput> fileOutputs = new ArrayList<>();

    @Given("Input is {string}")
    public void input_is(String string) {
        input = string.replace("\\n", "\n");
    }

    @Given("Suffix is {string}")
    public void suffix_is(String suffix) {
        this.suffix = suffix;
    }

    @When("I parse the input")
    public void i_parse_the_input() {
        final String groovy = App.toGroovy(input);
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setScriptBaseClass(SchemaScriptBase.class.getName());
        GroovyShell shell = new GroovyShell(compilerConfiguration);
        rootElement = (Element) shell.evaluate(groovy + "\n root");
    }

    @Then("The element output should be {string}")
    public void the_element_output_should_be(String string) {
        assertEquals(string, rootElement.toString());
    }

    @When("I convert to {string}")
    public void i_convert_to(final String typeString) {
        final OutputType outputType = Arrays.stream(OutputType.values())
                .filter(outputType1 -> outputType1.name().toUpperCase().contains(typeString.toUpperCase()))
                .findFirst().orElse(OutputType.IMMUTABLES);
        final String groovy = App.toGroovy(input);
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setScriptBaseClass(SchemaScriptBase.class.getName());
        GroovyShell shell = new GroovyShell(compilerConfiguration);
        Root root = (Root) shell.evaluate(groovy);
        Config config = new Config();
        config.setOutputType(outputType);
        config.setIncludeJacksonJson(includeJson);
        config.setClassnameSuffix(suffix);
        fileOutputs.addAll(codeGenerator.generate(config, root));
        output = fileOutputs.get(0).getText();
    }

    @Then("The code output should contain {string}")
    public void the_code_output_should_contain(String string) {
        assertThat(output, containsString(string.replace("\\", "\n")));
    }

}
