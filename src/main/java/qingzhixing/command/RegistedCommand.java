package qingzhixing.command;

import java.util.ArrayList;
import java.util.List;

/*
 * 用于管理已经注册过的命令
 */
public class RegistedCommand {
    private static final List<AbstractTextCommand> registedCommandList = new ArrayList<>();

    public static List<AbstractTextCommand> registedCommandList() {
        return registedCommandList;
    }

    public static void RegistCommand(AbstractTextCommand command) {
        registedCommandList.add(command);
    }

    private static void StaticInitialize(){
        RegistCommand(new TestCommand());
    }

    static {
        StaticInitialize();
    }
}
