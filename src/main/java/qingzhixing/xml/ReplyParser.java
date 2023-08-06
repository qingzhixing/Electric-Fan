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

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[]resources=resolver.getResources("classpath*:/*.kwd-reply.xml");
            for(Resource resource:resources){
                logger.info("Find keyword-reply file: "+resource.getFilename());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File[] files = FileConstructor.ScanFiles(".",filter);
        if(files == null||files.length==0){
            logger.warn("Find No keyword-reply files.");
        }else{
            for(File file:files){
                logger.info("Find keyword-reply file: "+file.getName());
            }
        }

        logger.info("Keyword-Reply parse done.");
    }

}
