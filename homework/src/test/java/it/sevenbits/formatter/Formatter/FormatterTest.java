package it.sevenbits.formatter.Formatter;

import it.sevenbits.formatter.Formatter.Exceptions.ReadException;
import it.sevenbits.formatter.Formatter.Lexer.ILexer;
import it.sevenbits.formatter.Formatter.LexerFactory.ILexerFactory;
import it.sevenbits.formatter.Formatter.LexerFactory.LexerFactory;
import it.sevenbits.formatter.Formatter.Reader.IReader;
import it.sevenbits.formatter.Formatter.Reader.StringReader;
import it.sevenbits.formatter.Formatter.Token.Token;
import it.sevenbits.formatter.Formatter.Writer.IWriter;
import it.sevenbits.formatter.Formatter.Writer.StringWriter;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FormatterTest {
    private IFormatter formatter;

    @Test
    public void mockCreateLexerTest() throws IOException, ReadException {
        IReader mockReader = mock(IReader.class);
        IWriter mockWriter = mock(IWriter.class);
        ILexerFactory mockLexerFactory = mock(ILexerFactory.class);

        when(mockLexerFactory.createLexer(mockReader)).thenReturn(mock(ILexer.class));

        formatter = new Formatter(mockLexerFactory);
        formatter.format(mockReader, mockWriter);

        verify(mockLexerFactory, times(1)).createLexer(any(IReader.class));
    }

    @Test
    public void mockFormatTest() throws IOException, ReadException {
        IReader mockReader = mock(IReader.class);
        IWriter mockWriter = mock(IWriter.class);
        ILexerFactory mockLexerFactory = mock(ILexerFactory.class);

        ILexer mockLexer = mock(ILexer.class);
        when(mockLexerFactory.createLexer(mockReader)).thenReturn(mockLexer);

        when(mockLexer.hasMoreTokens()).thenReturn(true).thenReturn(false);
        when(mockLexer.readToken()).thenReturn(new Token("word", "aaa"));

        formatter = new Formatter(mockLexerFactory);
        formatter.format(mockReader, mockWriter);

        verify(mockLexer, times(2)).hasMoreTokens();
        verify(mockWriter, times(3)).write(anyChar());
    }

    @Test
    public void formatTest() throws ReadException, IOException {
        StringBuilder newFormatString = new StringBuilder();

        StringReader stringReader = new StringReader("         public    class   HelloWorld     \n\n  \n     " +
                "{public static void main(String[] args){ System.out.println(\"Hello, World\");         } }");
        StringWriter stringWriter = new StringWriter(newFormatString);

        ILexerFactory lexerFactory = new LexerFactory();
        formatter = new Formatter(lexerFactory);

        formatter.format(stringReader, stringWriter);

        assertEquals("Wrong formatting", "public class HelloWorld {\n    public static void main(String[] args) " +
                "{\n        System.out.println(\"Hello, World\");\n    }\n}\n", newFormatString.toString());
    }
}
