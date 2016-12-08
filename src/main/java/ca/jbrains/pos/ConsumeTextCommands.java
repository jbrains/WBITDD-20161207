package ca.jbrains.pos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.stream.Stream;

public class ConsumeTextCommands {
    private final CommandInterpreter commandInterpreter;

    public ConsumeTextCommands(CommandInterpreter commandInterpreter) {
        this.commandInterpreter = commandInterpreter;
    }

    public void consume(Reader commandSource) {
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
