package lz78;

import lz78.coder.LZ78Coder;
import lz78.decoder.LZ78Decoder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SimpleStringTest {

    private final String text;

    public SimpleStringTest(final String text) {
        this.text = text;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"AABABBBABAABABBBABBABB"},
                {"abbbcaabbcbbcaaac"}
        });
    }

    @Test
    public void testCodeFromWiki() throws IOException {
        Reader inputReader = new StringReader(text);
        StringWriter outputWriter = new StringWriter();
        LZ78Coder coder = new LZ78Coder(inputReader, outputWriter);
        coder.code();
        String codeWord = outputWriter.toString();

        System.out.println("Compression ratio for \"" + text + "\": " + codeWord.length() / (double) text.length());

        StringWriter decodedOutputWriter = new StringWriter();
        LZ78Decoder decoder = new LZ78Decoder(new StringReader(codeWord), decodedOutputWriter);
        decoder.decode();
        String decodedOutput = decodedOutputWriter.toString();

        Assert.assertEquals(text, decodedOutput);
    }
}
