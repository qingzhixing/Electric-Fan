package qingzhixing;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.BotConfiguration;
import org.apache.logging.log4j.*;
import org.jetbrains.annotations.NotNull;
import qingzhixing.keyword.KeywordReplyHandler;
import qingzhixing.xml.*;

import java.io.File;

public final class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static @NotNull Bot ConstructBot() {
        // 验证码登陆
        return BotFactory.INSTANCE.newBot(Setting.botQQ(), BotAuthorization.byQRCode(), botConfiguration -> {
            botConfiguration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
            botConfiguration.setHeartbeatStrategy(BotConfiguration.HeartbeatStrategy.STAT_HB);
            botConfiguration.setCacheDir(new File("cache")); // 最终为 workingDir 目录中的 cache 目录
            botConfiguration.disableContactCache();
            botConfiguration.fileBasedDeviceInfo();
        });
    }
    public static void main(String[] args) {
        System.out.println("Running Electric-Fan!");

        KeywordReplyHandler.keywordReplies=ReplyParser.keywordReplies();

        Global.bot=ConstructBot();
        Global.bot.login();

        Global.master = Global.bot.getFriend(Setting.masterQQ());

        if(Global.master!=null){
            Global.master.sendMessage("Electric-Fan is online🥰🥰🥰");
        }

        ContactList<Group> groups= Global.bot.getGroups();
        for(Group group:groups){
            logger.info("Group: "+group.getId()+" "+group.getName());
        }

        Global.bot.getEventChannel().registerListenerHost(new EventListenerHost());
    }
}