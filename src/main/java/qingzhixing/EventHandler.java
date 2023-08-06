package qingzhixing;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import qingzhixing.keyword.KeywordReplyHandler;

public class EventHandler extends SimpleListenerHost {
    private static final Logger logger = LogManager.getLogger(EventHandler.class);

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception){
        // 处理事件处理时抛出的异常
        logger.error("Exception occurred in event handler", exception);
        logger.error("context: " + context);

    }

    @net.mamoe.mirai.event.EventHandler
    public void GroupMessageEventHandler(GroupMessageEvent event) {
        // 关键词回复
        String content = event.getMessage().contentToString();
        logger.info("Received message: "+content);
        String reply= KeywordReplyHandler.DoReply(content);
        if(reply!=null) {
            event.getSubject().sendMessage(reply);
        }
    }

}
