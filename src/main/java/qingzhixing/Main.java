package qingzhixing;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.utils.BotConfiguration;
import org.apache.logging.log4j.*;
import qingzhixing.xml.*;

import java.io.File;

public final class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static Bot ConstructBot() {
        // 验证码登陆
        return BotFactory.INSTANCE.newBot(Setting.botQQ(), BotAuthorization.byQRCode(), botConfiguration -> {
            botConfiguration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
            botConfiguration.setHeartbeatStrategy(BotConfiguration.HeartbeatStrategy.REGISTER);
            botConfiguration.setCacheDir(new File("cache")); // 最终为 workingDir 目录中的 cache 目录
            botConfiguration.enableContactCache();
        });
    }
    public static void main(String[] args) {
        System.out.println("Hello Electric-Fan!");
        logger.debug("Test Log4j");
        ReplyParser.keywordReplies();

        Bot bot=ConstructBot();
//        bot.login();

        Friend master = bot.getFriend(Setting.masterQQ());

        if(master!=null){
            master.sendMessage("Electric-Fan is online🥰🥰🥰");
        }
    }
}