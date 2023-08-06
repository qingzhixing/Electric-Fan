package qingzhixing.keyword;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/*
 *  关键词回复的数据结构，存储一个关键词,存储多个绑定的Reply对象
 */
public class KeywordReply implements Comparable<KeywordReply> {
    private static final Logger logger = LogManager.getLogger(KeywordReply.class);

    public String keyword;
    public ArrayList<Reply> replies;

    public int priority;

    /*
     * 根据replies优先度生成相应回复
     * @return 返回生成的随机回复，非法则返回null
     */
    public String GenerateReply(){
        if(keyword==null||replies==null|| replies.isEmpty()){
            logger.error("Find Illegal KeywordReply Object!");
            return null;
        }
        // 按 priority 从大到小排序 replies
        replies.sort((o1, o2) -> o2.priority - o1.priority);

        AtomicLong prioritySum = new AtomicLong();
        replies.forEach(reply -> prioritySum.addAndGet(reply.priority));

        // randomIndex 落在哪个 reply 的 priority 区间内就是哪个 reply 被回复
        long randomIndex = (long) (Math.random() * prioritySum.get()+1);
        for (Reply reply : replies) {
            randomIndex -= reply.priority;
            if (randomIndex <= 0) {
                return reply.reply;
            }
        }
        return null;
    }

    public KeywordReply(String keyword, ArrayList<Reply> replies, int priority) {
        this.keyword = keyword;
        this.replies = replies;
        this.priority = priority;
    }

    @Override
    public int compareTo(@NotNull KeywordReply other) {
        return priority - other.priority;
    }

    public KeywordReply(String keyword, ArrayList<Reply> replies) {
        this(keyword, replies, 1);
    }
}
