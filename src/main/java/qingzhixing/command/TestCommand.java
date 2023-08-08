package qingzhixing.command;

import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageSource;

public class TestCommand extends AbstractTextCommand {
    public TestCommand() {
        super("/test", CommandType.BEGIN,true,"该指令用于测试Bot的文本指令功能");
    }

    @Override
    protected boolean Execute(Message message, Member replyTarget) {
        replyTarget.sendMessage(description);
        return true;
    }
}
