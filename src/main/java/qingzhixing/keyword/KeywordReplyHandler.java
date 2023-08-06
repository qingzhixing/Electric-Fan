package qingzhixing.keyword;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/*
 * 处理字符串的关键词回复功能
 */
public class KeywordReplyHandler {
    public static ArrayList<KeywordReply> keywordReplies;

    private static final Logger logger = LogManager.getLogger(KeywordReplyHandler.class);

    /*
     * 根据input字符串返回对应回复
     * @param input 要回复的字符串
     * @return 对应回复，若没有回复或者出错返回null
     */
    public static String DoReply(String input) {
        if(keywordReplies==null||keywordReplies.isEmpty()){
            logger.error("keywordReplies is null or empty");
            return null;
        }

        // 根据 priority 从大到小排序
        keywordReplies.sort((o1, o2) -> o2.priority - o1.priority);

        // 检测关键词并回复
        for (KeywordReply keywordReply : keywordReplies) {
            if (input.contains(keywordReply.keyword)) {
                return keywordReply.GenerateReply();
            }
        }
        // 没有匹配的字符串
        return null;
    }
}
