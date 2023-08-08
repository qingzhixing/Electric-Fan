package qingzhixing.command;

/*
 * 所有文本指令的抽象父类
 */
public class AbstractTextCommand {
    // 用于检测指令在什么地方检测
    public enum CommandType {
        BEGIN,MIDDLE,END
    }
}
