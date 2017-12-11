package lz78.coder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class LZ78Coder {

    private static final int FIRST_OCCURRENCE = 0;
    private static final String CODE_FORMAT = "(%d;%c)";

    private final Writer outputWriter;
    private final Reader inputReader;
    private final Map<String, Integer> dictionary = new HashMap<>();

    public LZ78Coder(Reader inputReader, Writer outputWriter) {
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
    }

    public void code() throws IOException {
        int n = 1;
        String c = "";
        int s;
        while ((s = inputReader.read()) != -1) {
            char character = (char) s;
            if (!dictionary.containsKey(c + character)) {
                if (c.isEmpty()) {
                    outputWriter.write(String.format(CODE_FORMAT, FIRST_OCCURRENCE, character));
                    dictionary.put(String.valueOf(character), n++);
                } else {
                    outputWriter.write(String.format(CODE_FORMAT, dictionary.get(c), character));
                    dictionary.put(c + character, n++);
                }
                c = "";
            } else {
                c += character;
            }
        }
        inputReader.close();
        outputWriter.close();
    }

}
