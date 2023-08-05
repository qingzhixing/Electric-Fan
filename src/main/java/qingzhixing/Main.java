package qingzhixing;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import org.apache.logging.log4j.*;
import qingzhixing.xml.Setting;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        System.out.println("Hello Electric-Fan!");
        logger.debug("Test Log4j");
        Bot bot = BotFactory.INSTANCE.newBot(Setting.botQQ(), Setting.botPassword());
    }
}