package cn.zhaooo.jvm;

import cn.zhaooo.jvm.classpath.Classpath;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Method;

public class Main {

    public static void main(String[] args) {
        // Call JCommander.parse
        Cmd cmd = Cmd.parse(args);
        if (!cmd.ok || cmd.helpFlag) {
            Cmd.jCmd.usage();
            return;
        }
        if (cmd.versionFlag) {
            // 注意案例测试都是基于1.8，另外jdk1.9以后使用模块化没有rt.jar
            System.out.println("java version \"1.8.0\"");
            return;
        }
        startJVM(cmd);
    }

    private static void startJVM(Cmd cmd) {
        Classpath classpath = new Classpath(cmd.jre, cmd.classpath);
        ClassLoader classLoader = new ClassLoader(classpath);

        //  获取className
        String className = cmd.getMainClass().replace(".", "/");
        Class mainClass = classLoader.loadClass(className);
        Method mainMethod = mainClass.getMainMethod();
        if (null == mainMethod) {
            throw new RuntimeException("Main method not found in class " + cmd.getMainClass());
        }
        new Interpreter(mainMethod, cmd.verboseClassFlag, cmd.getAppArgs());
    }
}
