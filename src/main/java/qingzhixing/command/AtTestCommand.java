package qingzhixing.command;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Message;

public class AtTestCommand extends AbstractTextCommand {
    public AtTestCommand() {
        super("测试指令", "/test", CommandType.BEGIN, true, "该指令用于测试Bot的文本指令功能");
    }

    @Override
    protected boolean Execute(Message message, Contact replyTarget) {
        replyTarget.sendMessage(description);
        return true;
    }
}
