package qingzhixing.utilities;

import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageToolTest {

    @Test
    void extractPlainText() {
        MessageChain messageChain =new MessageChainBuilder()
                .append("123")
                .append("456")
                .build();
        var plainText = MessageTool.ExtractPlainText(messageChain);
        assert(plainText!=null);
        assertEquals("123456", plainText.get(0).toString());
    }
}