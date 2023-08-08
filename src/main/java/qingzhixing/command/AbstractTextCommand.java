package qingzhixing.command;

import net.mamoe.mirai.contact.Contact;
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

    protected boolean needAtBot;

    protected String description;

    protected String name;

    public String description() {
        return description;
    }

    public String name() {
        return name;
    }

    public String keyword() {
        return keyword;
    }

    public CommandType commandType() {
        return commandType;
    }

    public boolean needAtBot() {
        return needAtBot;
    }

    public AbstractTextCommand(String name, String keyword, CommandType commandType, boolean needAtBot, String description) {
        this.name = name;
        this.keyword = keyword;
        this.commandType = commandType;
        this.needAtBot = needAtBot;
        this.description = description;
    }

    /*
     * 进行指令匹配
     * @param message 传入的待处理Message
     * @param replyTarget 回复对象
     * @return 是否匹配，若匹配则返回是否阻断下一步操作(不让下一个command匹配)
     */
    public boolean Match(Message message, Contact replyTarget) {
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

        logger.info("Match Success");

        return Execute(message, replyTarget);
    }

    /*
     * 在匹配的基础上进行执行
     * @param message 原消息
     * @param replyTarget 回复对象
     * @return 是否阻断下一步操作
     */
    protected abstract boolean Execute(Message message, Contact replyTarget);
}
