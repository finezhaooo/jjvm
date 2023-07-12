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
        // 获取className
        String className = cmd.getMainClass().replace(".", "/");
        ClassFile classFile = loadClass(className, classpath);
        assert classFile != null;
        printClassInfo(classFile);
    }

    private static ClassFile loadClass(String className, Classpath classpath) {
        try {
            byte[] classData = classpath.readClass(className);
            return new ClassFile(classData);
        } catch (Exception e) {
            System.out.println("Could not find or load main class " + className);
            return null;
        }
    }

    private static void printClassInfo(ClassFile cf) {
        System.out.println("version: " + cf.getMajorVersion() + "." + cf.getMinorVersion());
        System.out.println("constants count：" + cf.getConstantPool().getSize());
        System.out.format("access flags：0x%x\n", cf.getAccessFlags());
        System.out.println("this class：" + cf.getClassName());
        System.out.println("super class：" + cf.getSuperClassName());
        System.out.println("interfaces：" + Arrays.toString(cf.getInterfaceNames()));
        System.out.println("fields count：" + cf.getFields().length);
        for (MemberInfo memberInfo : cf.getFields()) {
            System.out.format("%s\t\t%s\n", memberInfo.getName(), memberInfo.getDescriptor());
        }
        System.out.println("methods count: " + cf.getMethods().length);
        for (MemberInfo memberInfo : cf.getMethods()) {
            System.out.format("%s\t\t%s\n", memberInfo.getName(), memberInfo.getDescriptor());
        }
    }
}
