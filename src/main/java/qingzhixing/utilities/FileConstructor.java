package qingzhixing.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;

// 该类用于提供文件相关的辅助功能
public final class FileConstructor {
    private static final Logger logger = LogManager.getLogger(FileConstructor.class);
    /*
     * 用于根据path生成URL
     * @param path 在jar包内部的地址
     * @return 构造的url,失败则返回null
     */
    public static URL  getInnerResource(String path){
        URL url = FileConstructor.class.getResource(path);
        if (url == null) {
            url = FileConstructor.class.getClassLoader().getResource(path);
        }
        if(url==null){
            logger.error("Failed to get the inner resource: " + path);
        }
        return url;
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

    /*
     * 返回jar包内部的目录路径
     * @return jar包内部路径，失败返回null
     */
    public static String getInnerRootPath() {
        // 简洁通过log4j2.xml获取根路径
        URL url = FileConstructor.class.getResource("/log4j2.xml");
        if (url == null) {
            logger.error("Failed to get /log4j2.xml URL");
            return null;
        }
        String rootPath = url.getPath().substring(0, url.getPath().lastIndexOf("/")+1);
        if (rootPath == null) {
            logger.error("Failed to get the inner root path");
            return null;
        }
        return rootPath;
    }
}
