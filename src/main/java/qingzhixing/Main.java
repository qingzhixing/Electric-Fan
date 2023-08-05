package qingzhixing;

import org.apache.logging.log4j.*;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        System.out.println("Hello Eletric-Fan!");
        logger.debug("Test Log4j");
    }
}