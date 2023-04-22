package cn.zhaooo.jvm;

import cn.zhaooo.jvm.classpath.Classpath;

public class Main {

    public static void main(String[] args) {
        // Call JCommander.parse
        Cmd cmd = Cmd.parse(args);
        if (!cmd.ok || cmd.helpFlag) {
            System.out.println("Usage: <main class> [-options] class [args...]");
            return;
        }
        if (cmd.versionFlag) {
            System.out.println("java version \"1.8.0\"");
            return;
        }
        startJVM(cmd);
    }

    private static void startJVM(Cmd cmd) {
        Classpath cp = new Classpath(cmd.jre, cmd.classpath);
        System.out.printf("classpath：%s class：%s args：%s\n", cp, cmd.getMainClass(), cmd.getAppArgs());
        // Replace dots in main class name with slashes to get class file name
        String className = cmd.getMainClass().replace(".", "/");
        try {
            // Load class data from classpath using class file name
            byte[] classData = cp.readClass(className);
            System.out.println("classData：");
            for (byte b : classData) {
                // Print out class data in hex format
                System.out.print(String.format("%02x", b & 0xff) + " ");
            }
        } catch (Exception e) {
            System.out.println("Could not find or load main class " + cmd.getMainClass());
            e.printStackTrace();
        }
    }

}
