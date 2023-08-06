package qingzhixing.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import qingzhixing.keyword.KeywordReply;
import qingzhixing.utilities.FileConstructor;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ReplyParser {
    private static final Logger logger= LogManager.getLogger(ReplyParser.class);
    public static ArrayList<KeywordReply> keywordReplies() {
        return keywordReplies;
    }

    private static ArrayList<KeywordReply> keywordReplies;

    // 筛选以 ".kwd-reply.xml" 结尾的文件
    private static final FileFilter filter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            return file.getName().endsWith(".kwd-reply.xml");
        }
    };

    static{
        logger.info("Keyword-Reply parse start:");

        Resource[] resources = FileConstructor.GetInnerResources("*.kwd-reply.xml");
        if(resources==null||resources.length==0){
            logger.error("No keyword-reply found.");
        }else{
            for(Resource resource:resources){
                logger.debug("Parsing keyword-reply: "+resource.getFilename());
            }
        }

        logger.info("Keyword-Reply parse done.");
    }

}
