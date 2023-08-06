# 🦾 Electric-Fan - UserManual
这里是 Electric-Fan 用户手册。本文面向对开发并不熟悉，但希望使用 Electric-Fan 提供的服务支持的用户。 如果你要开发 Electric-Fan 或参与贡献 Electric-Fan 项目，请先阅读 [开发文档](./Readme.md)。

## ♻️ 如何配置
<details>
    <summary>1. 配置 设置文件</summary>

打开 `./src/main/resources/` 目录,找到 `settings.xml` 文件进行编辑  
您也可以创建一个名为 `settings-private.xml` 的文件，和 `settings.xml` 等效  

### 关键字解释
**botQQ - 您的 bot 的QQ id**  
**masterQQ - 您的 QQ id ，用于进行线上运行时通报**  
**Enabled - 是否启用该配置文件**

### ⭕️ 注意
同时启用两个文件 private 优先  
两个都不启用将会产生错误

</details>

<details>
    <summary>2. 配置 关键词回复</summary>

打开 `./src/main/resources/` 目录,新建一个 `(anything).kwd-reply.xml`，便会被识别并加载入bot

### 关键字解释
**name - 该关键词回复组的名字，将在读入后在控制台INFO输出**  

关键词的配置入下
```xml
<keywordData>
        <!--不写weight默认为1-->
        <keyword weight="1">
            test
        </keyword>
        <replies>
            <reply weight="1">
                Hello,World!
            </reply>

            <!--不写weight默认为1-->
            <reply>
                hello,world!
            </reply>
        </replies>
    </keywordData>
```
* 每个关键词回复被包含在一个 `keywordData` 中
  * 有一个 `weight` 属性，表示这个关键词检测的优先度，`weight` 越大优先度越高，默认为 `1`。  
* 一个 `keywordData` 包含一个 `keyword` 和 一个 `replies`。
* `keyword` 中是需要检测的关键词。
* `replies` 中包含多个 `reply`。
* `reply` 中填写需要回复的 文本
  * 有 `weigh` 属性，越大优先级越高，默认为 `1`。
</details>



## ✅ 如何运行

1. 进行配置
2. 在项目根目录下执行
```shell
mvn package
java -jar ./Electric-Fan/target/Electric-Fan-1.0.jar
```
