package lz78.coder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class LZ78Coder {

    private static final int FIRST_OCCURRENCE = 0;
    private static final String CODE_FORMAT = "%c%c";

    private final String outputFileName;
    private final String inputData;
    private final List<String> dictionary = new ArrayList<>();

    public LZ78Coder(String inputFileName, String outputFileName) throws IOException {
        Path inputFilePath = Paths.get(inputFileName);
        this.inputData = new String(Files.readAllBytes(inputFilePath));
        this.outputFileName = outputFileName;
    }

    public void code() throws IOException {
        final Writer outputWriter = createWriter();
        int i = 0;
        while (i < inputData.length()) {
            String restOfInput = inputData.substring(i);
            Optional<String> dictionaryEntry = dictionary.stream()
                    .sorted(Comparator.comparing(String::length).reversed())
                    .filter(restOfInput::startsWith)
                    .findFirst();
            if (dictionaryEntry.isPresent()) {
                String foundDictionaryEntry = dictionaryEntry.get();
                final int k = dictionary.indexOf(foundDictionaryEntry) + 1;
                final int m = foundDictionaryEntry.length();
                char nextChar = inputData.charAt(i + m);
                outputWriter.write(String.format(CODE_FORMAT, k, nextChar));
                dictionary.add(foundDictionaryEntry + nextChar);
                i += m + 1;
            } else {
                char currentChar = inputData.charAt(i);
                dictionary.add(String.valueOf(currentChar));
                outputWriter.write(String.format(CODE_FORMAT, FIRST_OCCURRENCE, currentChar));
                i++;
            }
        }
        outputWriter.close();
    }

    private BufferedWriter createWriter() throws IOException {
        return new BufferedWriter(new FileWriter(outputFileName));
    }
}
