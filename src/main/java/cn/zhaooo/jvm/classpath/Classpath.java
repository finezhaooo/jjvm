package cn.zhaooo.jvm.classpath;

import cn.zhaooo.jvm.classpath.impl.WildcardEntry;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author zhaooo3
 * @description: Classpath是指Java的运行环境在查找类文件或其他源文件时所使用的路径。Classpath可以包含多个目录、jar文件或zip文件，它们之间用分隔符隔开。
 * @date 4/21/23 2:05 PM
 */
public class Classpath {

    // 启动类路径
    private Entry bootstrapClasspath;
    //  扩展类路径
    private Entry extensionClasspath;
    //  用户类路径
    private Entry userClasspath;

    /**
     * -Xjre选项解析启动类路径和扩展类路径，-classpath/-cp选项解析用户类路径
     */
    public Classpath(String jreOption, String cpOption) {
        // Initialize the bootstrap and extension classpaths
        // "C:\Program Files\Java\jdk1.8.0_161\jre"
        bootstrapAndExtensionClasspath(jreOption);
        // Initialize the user-defined classpath
        // .\test\HelloWorld
        parseUserClasspath(cpOption);
    }

    /**
     * 解析启动类路径和扩展类路径
     */
    private void bootstrapAndExtensionClasspath(String jreOption) {
        // jre文件夹根目录 即C:\Program Files\Java\jdk1.8.0_161\jre
        String jreDir = getJreDir(jreOption);
        //..jre/lib/*
        String jreLibPath = Paths.get(jreDir, "lib") + File.separator + "*";
        bootstrapClasspath = new WildcardEntry(jreLibPath);

        //..jre/lib/ext/*
        String jreExtPath = Paths.get(jreDir, "lib", "ext") + File.separator + "*";
        extensionClasspath = new WildcardEntry(jreExtPath);
    }

    /**
     * 获取jre目录
     */
    private static String getJreDir(String jreOption) {
        if (jreOption != null && Files.exists(Paths.get(jreOption))) {
            return jreOption;
        }
        // jreOption==null or jreOption did not contain jre
        if (Files.exists(Paths.get("./jre"))) {
            return "./jre";
        }
        // current dir didn't contain jre
        String jh = System.getenv("JAVA_HOME");
        if (jh != null) {
            return Paths.get(jh, "jre").toString();
        }
        // system has no env variable named JAVA_HOME
        throw new RuntimeException("Can not find JRE folder!");
    }

    /**
     * 解析用户类路径
     */
    private void parseUserClasspath(String cpOption) {
        //  如果用户没有提供-classpath/-cp选项，则使用当前目录作为用户类路径
        if (cpOption == null) {
            cpOption = ".";
        }
        // cpOption可以是文件，文件夹，zip；所以使用Entry.create()
        // create path Entry of current dir or cpOption
        userClasspath = Entry.create(cpOption);
    }

    /**
     * 依次从启动类路径、扩展类路径和用户类路径中搜索class文件并读取
     * 最终都会调用DirEntry或者ZipEntry的readClass方法
     */
    public byte[] readClass(String className) throws Exception {
        className = className + ".class";

        // Search for class in bootstrap classpath
        try {
            return bootstrapClasspath.readClass(className);
        } catch (Exception ignored) {
            // If class not found in bootstrap classpath, ignore and move on
        }

        // Search for class in extension classpath
        try {
            return extensionClasspath.readClass(className);
        } catch (Exception ignored) {
            // ignored
        }

        // Search for class in user classpath
        return userClasspath.readClass(className);
    }
}
