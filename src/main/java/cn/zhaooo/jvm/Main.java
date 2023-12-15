package cn.zhaooo.jvm;

import cn.zhaooo.jvm.classpath.Classpath;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Method;

public class Main {

    public static void main(String[] args) {
        Cmd cmd = Cmd.parse(args);
        if (cmd.help) {
            System.out.println(VMConfig.helpInfo);
            return;
        }
        if (cmd.version) {
            // 注意案例测试都是基于1.8，另外jdk1.9以后使用模块化没有rt.jar
            System.out.printf("version %s\n", VMConfig.version);
            return;
        }
        startJVM(cmd);
    }

    private static void startJVM(Cmd cmd) {
        Classpath classpath = new Classpath(cmd.Xjre, cmd.classpath);
        ClassLoader classLoader = new ClassLoader(classpath);

        //  获取className
        String className = cmd.mainClassAndArgs.get(0).replace(".", "/");
        Class mainClass = classLoader.loadClass(className);
        Method mainMethod = mainClass.getMainMethod();
        if (null == mainMethod) {
            throw new RuntimeException("Main method not found in class " + cmd.mainClassAndArgs.get(0));
        }
        new Interpreter(mainMethod, cmd.debug, cmd.mainClassAndArgs.subList(1, cmd.mainClassAndArgs.size()).toString());
    }

    public static class VMConfig {
        public static String version = "1.8.0";
        public static String helpInfo = "Usage:-Xjre <jre path> -cp/-classpath <class search path> <main class> [args...]";
    }
}
