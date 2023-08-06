package qingzhixing.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.core.io.Resource;
import qingzhixing.keyword.KeywordReply;
import qingzhixing.keyword.Reply;
import qingzhixing.utilities.FileConstructor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReplyParser {
    private static final Logger logger= LogManager.getLogger(ReplyParser.class);
    public static ArrayList<KeywordReply> keywordReplies() {
        return keywordReplies;
    }

    private static ArrayList<KeywordReply> keywordReplies;

    // 筛选以 ".kwd-reply.xml" 结尾的文件

    /*
     * 根据KeywordData生成KeywordReply对象
     * @return  KeywordReply对象,失败返回null
     */
    private static KeywordReply KeywordDataHandler(Element data){
        logger.debug("Entering KeywordDataHandler");
        if(!Objects.equals(data.getName(), "keywordData")) {
            logger.error("Illegal Param:Element is not KeywordData Element");
            return null;
        }

        // 获取priority属性
        String priorityStr=data.getAttributeValue("priority");
        int priority;
        if(priorityStr==null){
            priority=1;
        }else{
            priority=Integer.parseInt(priorityStr);
        }
        if(priority<=0){
            logger.warn("Find one silence keywordData.");
            return null;
        }
        logger.debug("      priority: "+priority);

        // 获取keyword
        Element keywordElement=data.getChild("keyword");
        if(keywordElement==null){
            logger.error("Illegal Param:No keyword found.");
            return null;
        }
        String keyword=keywordElement.getText().trim();
        if(keyword.isEmpty()){
            logger.error("Illegal Param:keyword is empty.");
            return null;
        }
        logger.debug("      keyword: "+keyword);

        // 获取replies
        Element repliesElement=data.getChild("replies");
        if(repliesElement==null){
            logger.error("Illegal Param:No replies element found.");
            return null;
        }
        logger.debug("      replies found.");

        //  解析每个reply
        ArrayList<Reply> replies=new ArrayList<>();

        List<Element> replyElements=repliesElement.getChildren("reply");
        if(replyElements==null||replyElements.isEmpty()){
            logger.error("Illegal Param:No reply elements found.");
            return null;
        }
        logger.debug("      "+replyElements.size()+" reply elements found.");
        for(Element replyElement:replyElements){
            logger.debug("      Parsing new reply...");
            // 解析priority
            String replyPriorityStr=replyElement.getAttributeValue("priority");
            int replyPriority;
            if(replyPriorityStr==null){
                replyPriority=1;
            }
            else{
                replyPriority=Integer.parseInt(replyPriorityStr);
            }
            if(replyPriority<=0){
                logger.warn("Find one silence reply.");
                continue;
            }
            logger.debug("          replyPriority: "+replyPriority);

            // 解析内容
            String reply=replyElement.getText().trim();
            if(reply.isEmpty()){
                logger.warn("Find one silence reply.");
                continue;
            }
            logger.debug("          reply: "+reply);

            // 构造Reply对象
            Reply replyObj=new Reply(reply,replyPriority);
            replies.add(replyObj);
            logger.debug("      Parsing new reply done.");
        }

        if(replies.isEmpty()){
            logger.error("No Usable reply found.");
            return null;
        }

        logger.debug("Out of KeywordDataHandler");
        // 构造keywordReply对象并返回
        return new KeywordReply(keyword,replies,priority);
    }

    private static void ParseXML(URL url){
        SAXBuilder builder = new SAXBuilder();
        Document document;
        try {
            document = builder.build(url);
        } catch (JDOMException | IOException e) {
            logger.error("Cannot Construct Document Object");
            logger.error(e.getMessage());
            return;
        }
        Element root = document.getRootElement();

        // 获取名称
        Element nameElement = root.getChild("name");
        if(nameElement==null){
            logger.warn("Parsing keyword-reply: "+url.getPath()+" failed.");
            logger.warn("No name found.");
            return;
        }
        logger.info("Name: "+nameElement.getText());

        //获取 keywordDatas
        List<Element> keywordDatas = root.getChildren("keywordData");
        if(keywordDatas==null||keywordDatas.isEmpty()){
            logger.warn("Parsing keyword-reply: "+url.getPath()+" failed.");
            logger.warn("No keywordData found.");
            return;
        }
        logger.info(keywordDatas.size()+" KeywordDatas found: ");
        keywordReplies=new ArrayList<>();
        for(Element keywordData:keywordDatas){
            logger.info("Parsing new data...");
            KeywordReply keywordReply=KeywordDataHandler(keywordData);
            if(keywordReply!=null){
                keywordReplies.add(keywordReply);
            }
            logger.info("Parsing new data done.");
        }

    }

    static{
        logger.info("Keyword-Reply parse start:");

        // 读取文件
        Resource[] resources = FileConstructor.GetInnerResources("*.kwd-reply.xml");
        if(resources==null||resources.length==0){
            logger.error("No keyword-reply found.");
        }else{
            // 解析文件
            for(Resource resource:resources){
                logger.debug("Parsing keyword-reply: "+resource.getFilename());
                try {
                    ParseXML(resource.getURL());
                } catch (IOException e) {
                    logger.warn("Parsing keyword-reply: "+resource.getFilename()+" failed.");
                    logger.warn(e.getMessage());
                }
            }
        }

        logger.info("Keyword-Reply parse done.");
    }

}
