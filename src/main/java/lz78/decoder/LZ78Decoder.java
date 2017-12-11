package lz78.decoder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LZ78Decoder {
    private static final int FIRST_OCCURRENCE = 0;
    private static final String PATTERN = "(\\d+;\\S)";
    private static final String CODE_WORDS_DELIMITERS = "\\(|\\)\\(|\\)";

    private final Reader inputReader;
    private final Writer outputWriter;
    private final Map<Integer, String> dictionary = new HashMap<>();

    public LZ78Decoder(Reader inputReader, Writer outputWriter) {
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
    }

    public void decode() throws IOException {
        int n = 1;
        Scanner inputScanner = new Scanner(inputReader).useDelimiter(CODE_WORDS_DELIMITERS);
        while (inputScanner.hasNext(PATTERN)) {
            String codeWord = inputScanner.next(PATTERN);
            String [] splitted = codeWord.split(";");
            int i = Integer.parseInt(splitted[0]);
            String s = splitted[1];
            if (i == FIRST_OCCURRENCE) {
                outputWriter.write(s);
                dictionary.put(n++, s);
            } else {
                String newDictionaryEntry = dictionary.get(i) + s;
                outputWriter.write(newDictionaryEntry);
                dictionary.put(n++, newDictionaryEntry);
            }
        }
        inputReader.close();
        outputWriter.close();
    }
}
