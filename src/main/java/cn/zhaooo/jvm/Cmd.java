package cn.zhaooo.jvm;

import java.util.Arrays;
import java.util.List;

/**
 * cmd
 */
public class Cmd {
    String Xjre;
    boolean help = false;
    boolean version = false;
    boolean debug = false;
    String classpath;
    List<String> mainClassAndArgs;

    public static Cmd parse(String[] args) {
        Cmd cmd = new Cmd();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "-h":
                case "-help":
                    cmd.help = true;
                    break;
                case "-v":
                case "-version":
                    cmd.version = true;
                    break;
                case "-cp":
                case "-classpath":
                    cmd.classpath = args[++i];
                    break;
                case "-Xjre":
                    cmd.Xjre = args[++i];
                    break;
                case "-debug":
                    cmd.debug = true;
                    break;
                default:
                    if (arg.startsWith("-")) {
                        System.err.printf("Unrecognized option: %s\n", arg);
                        cmd.help = true;
                    } else {
                        cmd.mainClassAndArgs = Arrays.asList(args).subList(i, args.length);
                        return cmd;
                    }
            }
        }
        return cmd;
    }
}