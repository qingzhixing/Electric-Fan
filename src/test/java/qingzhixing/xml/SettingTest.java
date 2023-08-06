package qingzhixing.xml;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingTest {
    @Test
    public void testSetting() {
        assert (Setting.botQQ()!=0);
        assert (Setting.masterQQ()!=0);

    }
}