package ca.jbrains.pos.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.stream.Stream;

public class ConsumeTextCommandsTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void oneBarcode() throws Exception {
        final CommandInterpreter commandInterpreter = context.mock(CommandInterpreter.class);

        context.checking(new Expectations() {{
            oneOf(commandInterpreter).interpretCommand(with("::command text::"));
        }});

        new ConsumeTextCommands(commandInterpreter).consume(new StringReader("::command text::\n"));
    }

    @Test
    public void noBarcodes() throws Exception {
        final CommandInterpreter commandInterpreter = context.mock(CommandInterpreter.class);

        context.checking(new Expectations() {{
            never(commandInterpreter);
        }});

        new ConsumeTextCommands(commandInterpreter).consume(new StringReader(""));
    }

    @Test
    public void severalBarcodes() throws Exception {
        final CommandInterpreter commandInterpreter = context.mock(CommandInterpreter.class);

        context.checking(new Expectations() {{
            oneOf(commandInterpreter).interpretCommand(with("::command 1::"));
            oneOf(commandInterpreter).interpretCommand(with("::command 2::"));
            oneOf(commandInterpreter).interpretCommand(with("::command 3::"));
        }});

        new ConsumeTextCommands(commandInterpreter)
                .consume(new StringReader(
                        "::command 1::\n" +
                                "::command 2::\n" +
                                "::command 3::\n"
                ));
    }

    @Test
    public void emptyCommands() throws Exception {
        final CommandInterpreter commandInterpreter = context.mock(CommandInterpreter.class);

        context.checking(new Expectations() {{
            oneOf(commandInterpreter).interpretCommand(with("::command 1::"));
            oneOf(commandInterpreter).interpretCommand(with("::command 2::"));
            oneOf(commandInterpreter).interpretCommand(with("::command 3::"));
        }});

        new ConsumeTextCommands(commandInterpreter)
                .consume(new StringReader(
                        "\n" +
                                "\n" +
                                "::command 1::\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "::command 2::\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "::command 3::\n" +
                                "\n" +
                                "\n" +
                                "\n"
                ));
    }

    @Test
    public void insignificantWhitespace() throws Exception {
        final CommandInterpreter commandInterpreter = context.mock(CommandInterpreter.class);

        context.checking(new Expectations() {{
            oneOf(commandInterpreter).interpretCommand(with("::command 1::"));
            oneOf(commandInterpreter).interpretCommand(with("::command 2::"));
            oneOf(commandInterpreter).interpretCommand(with("::command 3::"));

        }});

        new ConsumeTextCommands(commandInterpreter)
                .consume(new StringReader(
                        "\t\t\n" +
                                "\n" +
                                "   \t ::command 1:: \t  \n" +
                                "\n" +
                                "  \t\t  \n" +
                                "\n" +
                                "  \t  ::command 2::\n" +
                                "      \n" +
                                "\n" +
                                "\n" +
                                "::command 3::         \t\n" +
                                "\n" +
                                "     \t     \n" +
                                "\n"
                ));
    }

    public interface CommandInterpreter {
        void interpretCommand(String commandText);
    }

    public static class ConsumeTextCommands {
        private final CommandInterpreter commandInterpreter;

        public ConsumeTextCommands(CommandInterpreter commandInterpreter) {
            this.commandInterpreter = commandInterpreter;
        }

        public void consume(Reader commandSource) throws IOException {
            sanitizeCommands(streamCommands(commandSource))
                    .forEachOrdered(commandInterpreter::interpretCommand);
        }

        private Stream<String> sanitizeCommands(Stream<String> commandStream) {
            return commandStream
                    .map(String::trim)
                    .filter((line) -> !line.isEmpty());
        }

        private Stream<String> streamCommands(Reader commandSource) {
            return new BufferedReader(commandSource).lines();
        }
    }
}
