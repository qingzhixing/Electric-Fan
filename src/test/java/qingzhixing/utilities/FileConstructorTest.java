package qingzhixing.utilities;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class FileConstructorTest {

    @Test
    void getInnerResource() {
        try {
            URL url = FileConstructor.getInnerResource("/settings-private.xml");
            System.out.println(url);
        }catch (Exception e){
            System.out.println(e.getMessage());
            assert(false);
        }
    }
}