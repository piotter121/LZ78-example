import lz78.coder.LZ78Coder;
import lz78.decoder.LZ78Decoder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

public class SimpleTest {

    @Test
    public void testCodeFromWiki() throws IOException {
        final String text = "abbbcaabbcbbcaaac";
        Reader inputReader = new StringReader(text);
        StringWriter outputWriter = new StringWriter();
        LZ78Coder coder = new LZ78Coder(inputReader, outputWriter);
        coder.code();
        final String codeWord = outputWriter.toString();
        System.out.println(codeWord);

        StringWriter decodedOutputWriter = new StringWriter();
        LZ78Decoder decoder = new LZ78Decoder(new StringReader(codeWord), decodedOutputWriter);
        decoder.decode();
        String decodedOutput = decodedOutputWriter.toString();

        Assert.assertEquals(text, decodedOutput);
    }
}
