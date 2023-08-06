package qingzhixing.keyword;

import org.jetbrains.annotations.NotNull;

public class Reply implements Comparable<Reply>{
    public int priority;
    public String reply;

    public Reply( String reply,int priority) {
        this.priority = priority;
        this.reply = reply;
    }

    public Reply(String reply){
        this( reply,1);
    }

    @Override
    public int compareTo(@NotNull Reply other) {
        return priority -other.priority;
    }
}
