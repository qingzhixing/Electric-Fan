[![wakatime](https://wakatime.com/badge/user/cd8731d7-2366-4da2-8032-5bb5ad0d3122/project/4287dc1c-2b18-48d4-b88d-a5c722eaaa34.svg)](https://wakatime.com/badge/user/cd8731d7-2366-4da2-8032-5bb5ad0d3122/project/4287dc1c-2b18-48d4-b88d-a5c722eaaa34)
# 🥰 Electric-Fan
这是一个私人的Mirai QQ Robor  
🦾电风扇是我的QQ机器人的名字，欢迎光临！

## ♻️ 如何配置:
打开 `./src/main/resources/` 目录,找到 `settings.xml` 文件进行编辑  
您也可以创建一个名为 `settings-private.xml` 的文件，和 `settings.xml` 等效
> botQQ - 您的 bot 的QQ id  
> masterQQ - 您的 QQ id ，用于进行线上运行时通报  
> Enabled - 是否启用该配置文件  

### ⭕️ 注意：
同时启用两个文件 private 优先  
两个都不启用将会产生错误

## ✅ 如何运行:

1. 配置 `settings.xml` 或者 `settings-private.xml`
2. 在项目根目录下执行:
```shell
mvn package
java -jar ./Electric-Fan/target/Electric-Fan-1.0.jar
```

## 🌈 ToDo List:

- [ ] 支持关键词回复
- [ ] 实现外置 `settings.xml` 的读取