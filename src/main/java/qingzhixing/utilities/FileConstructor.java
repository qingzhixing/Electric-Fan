package qingzhixing.utilities;

import java.net.URL;

// 该类用于提供文件相关的辅助功能
public final class FileConstructor {
    /*
     * 用于根据path生成URL
     * @param path 在jar包内部的地址
     * @return 构造的url
     */
    public static URL  getInnerResource(String path) throws RuntimeException{
        URL url = FileConstructor.class.getResource(path);
        if (url == null) {
            url = FileConstructor.class.getClassLoader().getResource(path);
        }
        if(url==null){
            throw new RuntimeException("Can't find or construct resource: " + path);
        }
        return url;
    }
}
