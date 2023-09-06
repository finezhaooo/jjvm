package cn.zhaooo.jvm;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.List;

/**
 * cmd
 * C
 */
public class Cmd {
    @Parameter(names = "-Xjre", description = "path to jre", order = 4)
    String jre;

    @Parameter(names = {"-?", "-help"}, description = "print help message", order = 3, help = true)
    boolean helpFlag = false;

    @Parameter(names = "-verbose", description = "enable verbose output", order = 5)
    boolean verboseClassFlag = false;

    @Parameter(names = "-version", description = "print version and exit", order = 2)
    boolean versionFlag = false;

    @Parameter(names = {"-cp", "-classpath"}, description = "classpath", order = 1)
    String classpath;

    @Parameter(description = "main class and args")
    List<String> mainClassAndArgs;

    // successful parse
    boolean ok;

    static JCommander jCmd;

    String getMainClass() {
        return mainClassAndArgs != null && !mainClassAndArgs.isEmpty()
                ? mainClassAndArgs.get(0)
                : null;
    }

    String getAppArgs() {
        return mainClassAndArgs != null && mainClassAndArgs.size() > 1
                ? String.join(" ", mainClassAndArgs.subList(1, mainClassAndArgs.size())) : null;
    }

    static Cmd parse(String[] argv) {
        Cmd args = new Cmd();
        jCmd = JCommander.newBuilder().programName("jjvm").addObject(args).build();
        // JCommander.parse
        jCmd.parse(argv);
        args.ok = true;
        // return Cmd
        return args;
    }
}