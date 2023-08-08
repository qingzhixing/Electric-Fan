package qingzhixing.command;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import qingzhixing.utilities.MessageTool;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class AtAskQuestion extends AbstractTextCommand {

    private static final String[] deleteStrings={"吗","嘛","?","？"};

    public AtAskQuestion() {
        // 任意条件匹配，只要atBot就能匹配
        super("Ask Questions","", CommandType.END, true, "将会返回你的问句，但是去掉疑问词，是真正的百万级AI");
    }

    @Override
    protected boolean Execute(Message message, Contact replyTarget) {
        List<PlainText> plainTextList = MessageTool.ExtractPlainText(message);

        AtomicBoolean okFlag = new AtomicBoolean(false);
        plainTextList.forEach(plainText -> {
            String content = plainText.toString().trim();
            String replyContent = content;
            // 删除字符
            for (String deleteString : deleteStrings) {
                replyContent = replyContent.replace(deleteString, "");
            }
            if(replyContent.equals(content)){
                return;
            }
            // 成功回答则标记flag
            okFlag.set(true);
            replyTarget.sendMessage(replyContent);
        });

        return okFlag.get();
    }
}
