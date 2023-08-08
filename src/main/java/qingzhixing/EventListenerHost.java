package qingzhixing;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import qingzhixing.keyword.KeywordReplyHandler;

import java.util.Objects;

public class EventListenerHost extends SimpleListenerHost {
    private final Friend master;
    private static final Logger logger = LogManager.getLogger(EventListenerHost.class);

    public EventListenerHost(Friend master) {
        this.master = master;
    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
        logger.error("Exception occurred in event handler", exception);
        logger.error("context: " + context);

        // 报告master
        if (master != null) {
            master.sendMessage("Exception occurred in event handler: " + exception.getMessage());
        }
    }

    @EventHandler
    public void MessageEventHandler(MessageEvent event) {
        // 关键词回复
        String content = event.getMessage().contentToString();
        String reply = KeywordReplyHandler.DoReply(content);
        if (reply != null) {
            event.getSubject().sendMessage(reply);
        }
    }

    @EventHandler
    public void BotInvitedJoinGroupRequestEventHandler(BotInvitedJoinGroupRequestEvent event) {
        // 直接同意
        event.accept();

        // 报告master
        if (master != null) {
            master.sendMessage("Bot invited join group request: " + event.getGroupId() + " " + event.getGroupName());
        }

    }

    @EventHandler
    public void FriendRequestHandler(NewFriendRequestEvent event) {
        // 直接同意
        event.accept();

        // 报告master
        if (master != null) {
            master.sendMessage("New friend request: " + event.getFromId() + " " + event.getFromNick());
        }

        // 开启问好协程
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Objects.requireNonNull(event.getBot().getFriend(event.getFromId())).sendMessage("你好泥塑随");
            } catch (Exception e) {
                logger.error("Thread Error: " + e.getMessage());
            }
        }).start();
    }

}
