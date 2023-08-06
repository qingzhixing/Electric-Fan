package qingzhixing.utilities;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

class FileConstructorTest {

    @Test
    void getInnerResource() {
        URL url = null;
        try {
            url = Objects.requireNonNull(FileConstructor.GetInnerResource("log4j2.xml")).getURL();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assert (url!=null);
    }
}