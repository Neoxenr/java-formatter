package it.sevenbits.formatter.Formatter.Command;

import it.sevenbits.formatter.Formatter.Token.IToken;
import it.sevenbits.formatter.Formatter.Writer.IWriter;

import java.io.IOException;

public class IndentNewlineMacroCommand implements ICommand {
    private final IToken token;
    private final IWriter writer;
    private final IndentCommand indentCommand;
    private final WriteCommand writeCommand;
    private final NewlineCommand newlineCommand;

    /**
     * Command which is adding indent, switching to a new line and writing symbols in output stream
     * @param token - token
     * @param writer - output stream
     */
    public IndentNewlineMacroCommand(final IToken token, final IWriter writer) {
        this.token = token;
        this.writer = writer;
        this.indentCommand = new IndentCommand(token, writer);
        this.writeCommand = new WriteCommand(token, writer);
        this.newlineCommand = new NewlineCommand(token, writer);
    }

    /**
     * This method executes three commands
     * @throws IOException - stream's error
     */
    public void execute() throws IOException {
        indentCommand.execute();
        writeCommand.execute();
        newlineCommand.execute();
    }
}
