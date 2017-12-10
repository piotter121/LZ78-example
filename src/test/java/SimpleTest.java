import lz78.coder.LZ78Coder;
import org.junit.Test;

import java.net.URL;

public class SimpleTest {

    @Test
    public void testFromWiki() throws Exception {
        URL filepath = getClass().getResource("input.txt");
        LZ78Coder coder = new LZ78Coder(filepath.getPath(), "output.txt");
        coder.code();
    }
}
