package qingzhixing.xml;

import org.apache.logging.log4j.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import qingzhixing.utilities.FileConstructor;

public final class Setting {
    private static final Logger logger = LogManager.getLogger(Setting.class);

    public static Long botQQ() {
        return botQQ;
    }

    private static Long botQQ;
    private static Long masterQQ;

    public static Long masterQQ() {
        return masterQQ;
    }

    // 解析xml构造静态变量
    static{
        SAXBuilder builder = new SAXBuilder();
        try {
            logger.debug("Parsing private settings xml start:");
            Document document = builder.build(FileConstructor.getInnerResource("/settings-private.xml"));
            Element root = document.getRootElement();

            botQQ = Long.parseLong(root.getChild("BotQQ").getText());
            masterQQ = Long.parseLong(root.getChild("MasterQQ").getText());

            logger.debug("botQQ: " + botQQ);
            logger.debug("masterQQ: " + masterQQ);
            logger.debug("Parsing private settings xml done.");
        } catch (Exception e) {
            logger.warn("Exception Occur!");
            logger.warn(e.getMessage());
        }
    }

}
