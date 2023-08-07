package qingzhixing.xml;

import org.apache.logging.log4j.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import qingzhixing.utilities.FileConstructor;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

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
     * 解析URL指向的settings.xml文件
     * @param url 一个指向settings.xml文件的URL
     * @return 返回是否设置合法
     */
    private static boolean ParseSettingsFile(URL url) {
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(url);
            Element root = document.getRootElement();

            Element botQQElement = root.getChild("botQQ");
            Element masterQQElement = root.getChild("masterQQ");
            Element enabledElement = root.getChild("enabled");

            // 判断xml文件本身是否合法
            if (botQQElement == null || masterQQElement == null || enabledElement == null) {
                logger.warn("xml file is not legal!");
                return false;
            }

            // 判断是否启用
            if (!enabledElement.getText().equals("True")) {
                logger.warn("xml file is not enabled!");
                return false;
            }

            // 根据文件设置静态变量
            botQQ = Long.parseLong(botQQElement.getText());
            masterQQ = Long.parseLong(masterQQElement.getText());
            logger.debug("      botQQ: " + botQQ);
            logger.debug("      masterQQ: " + masterQQ);

        } catch (Exception e) {
            logger.warn("Exception Occur!");
            logger.warn(e.getMessage());
            return false;
        }
        return true;
    }

    /*
     * 用于解析jar包内的xml文件
     * @param inner path xml文件路径
     * @return 返回是否设置合法
     */
    private static boolean ParseInnerSettingsFile(String path) {
        logger.debug("  parsing inner: " + path);
        try {
            URL url = Objects.requireNonNull(FileConstructor.GetInnerResource(path)).getURL();
            return ParseSettingsFile(url);
        } catch (IOException e) {
            logger.warn("Cannot get URL of inner path: " + path);
            return false;
        }
    }

    /*
     * 用于解析jar包外部的xml文件
     * @param outer path xml文件路径
     * @return 返回是否设置合法
     */
    private static boolean ParseOuterSettingsFile(String dir,String fileName){
        FileFilter filter = (File file) -> file.getName().trim().equals(fileName.trim());
        File[] files = FileConstructor.ScanOuterFiles(dir,filter);

        if(files == null){
            logger.warn("Cannot get file: " + fileName+" in directory: " + dir);
            return false;
        }

        logger.debug("Find files: "+files.length+" in directory: "+dir+" with name: "+fileName);
        for(File f:files){
            logger.debug("  "+f.getName());
        }

        if(files.length==0){
            logger.warn("files is empty");
            return false;
        }

        try {
            URL url = files[0].toURI().toURL();
            return ParseSettingsFile(url);
        } catch (MalformedURLException e) {
            logger.error("Cannot get URL of file: "+files[0].getName());
            return false;
        }
    }

    // 解析xml构造静态变量
    private static void StaticInitialize() {
        logger.info("Parsing settings xml start:");
        logger.info("Parsing outer settings xml...");
        // 解析外部设置
        if (ParseOuterSettingsFile(".","settings.xml")) {
            logger.info("Parsing settings xml done.");
            return;
        }
        logger.warn("Not Exists or Not Enabled or Not Legal!");
        logger.debug("Parsing private settings xml...");

        // 解析内部private 设置
        if (ParseInnerSettingsFile("settings-private.xml")) {
            logger.info("Parsing settings xml done.");
            return;
        }
        logger.warn("Not Exists or Not Enabled or Not Legal!");
        logger.debug("Parsing public settings xml...");

        // 解析内部public 设置
        if (ParseInnerSettingsFile("settings.xml")) {
            logger.info("Parsing settings xml done.");
            return;
        }
        logger.error("Not Exists or Not Enabled or Not Legal!");
        logger.error("!!!!!PANIC:No Existing or Legal or Enabled Settings!!!!!");

    }

    static {
        StaticInitialize();
    }

}
