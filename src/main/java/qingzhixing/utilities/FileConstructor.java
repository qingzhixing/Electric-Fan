package qingzhixing.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

// 该类用于提供文件相关的辅助功能
public final class FileConstructor {
    private static final Logger logger = LogManager.getLogger(FileConstructor.class);
    /*
     * 用于根据path生成Resources,支持*.*格式
     * @param path 在jar包内部的地址，无前缀'/'
     * @return 找到的Resource[],失败则返回null
     */
    public static Resource[] GetInnerResources(String path){
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            return resolver.getResources("classpath*:/"+path);
        } catch (IOException e) {
            logger.error("Fail to get inner resources: " + path);
            logger.error(e.getMessage());
            return null;
        }
    }

    /*
     * 返回符合path的第一个 Resource,支持*.*格式
     * @param path 在jar包内部的地址，无前缀'/'
     * @return 找到的Resource[]中的第一个,失败则返回null
     */
    public static Resource GetInnerResource(String path){
        Resource[] resources = GetInnerResources(path);
        if(resources == null || resources.length == 0){
            logger.error("Fail to get single inner resource: " + path);
            return null;
        }
        return resources[0];
    }

    /*
     * 在目录 dir 中用 filter 筛选出文件并返回
     * @param dir 要扫描的目录
     * @param filter FileFilter对象
     * @return 返回筛选后的文件数组,失败返回null
     */
    public static File[]  ScanFiles(String dir, FileFilter filter){
        File directory = new File(dir);
        if(!directory.isDirectory()){
            logger.error("The directory: " + dir + " is not a directory");
            return null;
        }
        return directory.listFiles(filter);
    }
}
