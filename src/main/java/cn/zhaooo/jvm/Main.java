package cn.zhaooo.jvm;

import cn.zhaooo.jvm.classfile.ClassFile;
import cn.zhaooo.jvm.classfile.MemberInfo;
import cn.zhaooo.jvm.classpath.Classpath;

import java.util.Arrays;

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
        System.out.printf("classpath：%s class：%s args：%s\n", classpath, cmd.getMainClass(), cmd.getAppArgs());
        //获取className
        String className = cmd.getMainClass().replace(".", "/");
        try {
            byte[] classData = classpath.readClass(className);
            System.out.println("classData：");
            for (byte b : classData) {
                //16进制输出
                System.out.print(String.format("%02x", b & 0xff) + " ");
            }
        } catch (Exception e) {
            System.out.println("Could not find or load main class " + cmd.getMainClass());
            e.printStackTrace();
        }
    }
}
