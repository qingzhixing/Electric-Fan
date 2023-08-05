package qingzhixing.utilities;

import java.net.URL;

// 该类用于提供文件相关的辅助功能
public final class FileConstructor {
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
        return url;
    }
}
