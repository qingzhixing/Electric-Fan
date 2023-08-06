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

    /*
     * 用于解析jar包内的xml文件
     * @param path xml文件路径
     * @return 返回是否设置合法
     */
    private static boolean ParseInnerSettingsFile(String path){
        logger.debug("  parsing: "+path);
        SAXBuilder builder = new SAXBuilder();
        try {

            Document document = builder.build(FileConstructor.getInnerResource(path));
            Element root = document.getRootElement();

            Element botQQElement = root.getChild("BotQQ");
            Element masterQQElement = root.getChild("MasterQQ");
            Element enabledElement = root.getChild("Enabled");

            // 判断xml文件本身是否合法
            if(botQQElement==null || masterQQElement==null || enabledElement==null){
                logger.warn("xml file is not legal!");
                return false;
            }

            // 判断是否启用
            if(!enabledElement.getText().equals("True")){
                logger.warn("xml file is not enabled!");
                return false;
            }

            // 根据文件设置静态变量
            botQQ=Long.parseLong(botQQElement.getText());
            masterQQ=Long.parseLong(masterQQElement.getText());
            logger.debug("      botQQ: "+botQQ);
            logger.debug("      masterQQ: "+masterQQ);

        } catch (Exception e) {
            logger.warn("Exception Occur!");
            logger.warn(e.getMessage());
            return false;
        }
        return true;
    }

    // 解析xml构造静态变量
    static{
        logger.info("Parsing settings xml start:");
        logger.debug("Parsing private settings xml...");
        if(!ParseInnerSettingsFile("/settings-private.xml")){
            logger.error("Not Exists or Not Enabled or Not Legal!");
            if(!ParseInnerSettingsFile("/settings.xml")){
                logger.debug("Parsing public settings xml...");
                logger.error("Not Exists or Not Enabled or Not Legal!");
                logger.error("!!!!!PANIC:No Existing or Legal or Enabled Settings!!!!!");
            }
        }
        logger.info("Parsing settings xml done.");
    }

}
