package lz78.decoder;

import lz78.commons.CodePair;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LZ78Decoder {
    private static final int FIRST_OCCURRENCE = 0;

    private final Reader inputReader;
    private final Writer outputWriter;
    private final Map<Integer, String> dictionary = new HashMap<>();
    private final List<CodePair> input = new ArrayList<>();

    public LZ78Decoder(Reader inputReader, Writer outputWriter) {
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
    }

    public void decode() throws IOException {
        try {
            readInput();
            int n = 1;
            for (CodePair codePair : input) {
                int index = codePair.getIndex();
                char character = codePair.getCharacter();
                if (index == FIRST_OCCURRENCE) {
                    outputWriter.write(character);
                    dictionary.put(n++, String.valueOf(character));
                } else {
                    String newDictionaryEntry = dictionary.get(index);
                    if (character != '\u0000') newDictionaryEntry += character;
                    outputWriter.write(newDictionaryEntry);
                    dictionary.put(n++, newDictionaryEntry);
                }
            }
        } finally {
            inputReader.close();
            outputWriter.close();
        }
    }

    private void readInput() throws IOException {
        CharBuffer charBuffer = CharBuffer.allocate(2);
        while (inputReader.read(charBuffer) == 2) {
            int index = charBuffer.get(0);
            char character = charBuffer.get(1);
            input.add(new CodePair(index, character));
            charBuffer.clear();
        }
    }
}
