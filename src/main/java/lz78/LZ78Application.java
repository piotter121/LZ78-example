package lz78;

import lz78.coder.LZ78Coder;
import lz78.decoder.LZ78Decoder;

import java.io.IOException;

public class LZ78Application {
    private static final String USAGE = "[mode] [input_file] [output_file]\n" +
            "mode - coder [-c] or decoder [-d] mode";
    private static final String CODER_MODE = "-c";
    private static final String DECODER_MODE = "-d";

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println(USAGE);
        }

        final String mode = args[0];
        final String inputFile = args[1];
        final String outputFile = args[2];

        switch (mode) {
            case CODER_MODE:
                LZ78Coder coder = new LZ78Coder(inputFile, outputFile);
                coder.code();
                break;
            case DECODER_MODE:
                LZ78Decoder decoder = new LZ78Decoder(inputFile, outputFile);
                decoder.decode();
                break;
            default:
                throw new IllegalArgumentException("Unknown mode [" + mode + "]");
        }
    }
}
