package qingzhixing.utilities;

import org.junit.jupiter.api.Test;

import java.net.URL;

class FileConstructorTest {

    @Test
    void getInnerResource() {
        URL url = FileConstructor.getInnerResource("/settings.xml");
        assert (url!=null);
    }
}