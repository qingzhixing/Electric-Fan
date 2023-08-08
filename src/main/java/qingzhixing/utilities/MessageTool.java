package qingzhixing.utilities;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class MessageTool {
    private static final Logger logger = LogManager.getLogger(MessageTool.class);

    /*
     * 从 message 中提取 Plain Text 并返回一个 List
     * @param message 收到的Message
     * @return 提取之后的 PlainText List,失败返回null
     */
    public static List<PlainText> ExtractPlainText(@NotNull Message message) {
        // message 是 Chain 时
        if (message instanceof MessageChain) {
            MessageChain messageChain = (MessageChain) message;
            return List.of(
                    messageChain.stream().filter(
                            singleMessage -> (singleMessage instanceof PlainText)).map(PlainText.class::cast
                    ).toArray(PlainText[]::new)
            );
        }
        // message 是 SingleMessage时
        if(message instanceof PlainText){
            return List.of((PlainText) message);
        }else{
            logger.warn("Cannot Extract PlainText from message: "+message);
            return null;
        }
    }
}
