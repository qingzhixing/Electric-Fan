package qingzhixing.xml;

import org.apache.logging.log4j.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;

public final class Setting {
    private static final Logger logger = LogManager.getLogger(Setting.class);

    public static Long botQQ() {
        return botQQ;
    }

    private static Long botQQ;
    private static String botPassword;

    public static String botPassword() {
        return botPassword;
    }

    // 解析xml构造静态变量
    static{
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(Setting.class.getResource("/settings-private.xml"));
            Element root = document.getRootElement();
            botQQ = Long.parseLong(root.getChild("botQQ").getText());
            botPassword = root.getChild("botPassword").getText();
            logger.debug("botQQ: " + botQQ);
            logger.debug("botPassword: " + botPassword);
        } catch (Exception e) {
            logger.warn("Exception Occur!");
            logger.warn(e.getMessage());
        }
    }

}
