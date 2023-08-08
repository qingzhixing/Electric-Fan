package qingzhixing.command;

import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import qingzhixing.Global;
import qingzhixing.utilities.MessageTool;

/*
 * 所有文本指令的抽象父类
 */
public abstract class AbstractTextCommand {
    private static final Logger logger = LogManager.getLogger(AbstractTextCommand.class);

    // 用于检测指令在什么地方检测
    public enum CommandType {
        BEGIN, MIDDLE, END
    }

    protected String keyword;

    protected CommandType commandType;

    public String keyword() {
        return keyword;
    }

    public CommandType commandType() {
        return commandType;
    }

    public boolean needAtBot() {
        return needAtBot;
    }

    protected boolean needAtBot;

    public AbstractTextCommand() {
        keyword = "NONE!";
        commandType = CommandType.BEGIN;
        needAtBot = true;
    }

    /*
     * 进行指令匹配
     * @param message 传入的待处理Message
     * @return 是否匹配，若匹配则返回是否阻断下一步操作(不让下一个command匹配)
     */
    public boolean Match(Message message) {
        logger.info("Matching command: " + keyword + " in " + message.toString());

        // 判断At
        if (needAtBot) {
            var atList = MessageTool.ExtractAt(message);
            if (atList == null || atList.isEmpty()) {
                logger.info("No At in message");
                return false;
            }
            if (!atList.contains(new At(Global.bot.getId()))) {
                logger.info("No At bot in message");
                return false;
            }
        }

        // 判断是否match keyword
        String plainTextString = MessageTool.PlainTextListToString(MessageTool.ExtractPlainText(message)).trim();
        switch (commandType) {
            case BEGIN:
                if (!plainTextString.startsWith(keyword)) return false;
                break;
            case MIDDLE:
                if (!plainTextString.contains(keyword)) return false;
                break;
            case END:
                if (!plainTextString.endsWith(keyword)) return false;
                break;
        }

        return Execute(message);
    }

    /*
     * 在匹配的基础上进行执行
     * @param message 原消息
     * @return 是否阻断下一步操作
     */
    protected abstract boolean Execute(Message message);
}
