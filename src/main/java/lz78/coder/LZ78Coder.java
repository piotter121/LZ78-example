package lz78.coder;

import lz78.commons.CodePair;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lz78.LZ78Application.EOS;

public class LZ78Coder {

    private static final int FIRST_OCCURRENCE = 0;

    private final Writer outputWriter;
    private final Reader inputReader;
    private final Map<String, Integer> dictionary = new HashMap<>();
    private final List<CodePair> output = new ArrayList<>();

    public LZ78Coder(Reader inputReader, Writer outputWriter) {
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
    }

    public void code() throws IOException {
        try {
            processCoding();
            writeOutput();
        } finally {
            inputReader.close();
            outputWriter.close();
        }
    }

    private void processCoding() throws IOException {
        int n = 1;
        String c = "";
        int s;
        while ((s = inputReader.read()) != EOS) {
            char character = (char) s;
            if (!dictionary.containsKey(c + character)) {
                CodePair codePair;
                String word;
                if (c.isEmpty()) {
                    codePair = new CodePair(FIRST_OCCURRENCE, character);
                    word = String.valueOf(character);
                } else {
                    codePair = new CodePair(dictionary.get(c), character);
                    word = c + character;
                }
                output.add(codePair);
                dictionary.put(word, n++);
                c = "";
            } else {
                c += character;
            }
        }
        if (!c.isEmpty()) {
            int index = dictionary.get(c);
            CodePair codePair = new CodePair(index, (char) 0);
            output.add(codePair);
        }
    }

    private void writeOutput() throws IOException {
        for (CodePair codePair : output) {
            outputWriter.write(codePair.getIndex());
            outputWriter.write(codePair.getCharacter());
        }
    }

    @Override
    public String toString() {
        return "LZ78Coder{" +
                "dictionary=" + dictionary +
                ", output=" + output +
                '}';
    }
}
