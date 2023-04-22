package cn.zhaooo.jvm.classpath;

import cn.zhaooo.jvm.classpath.impl.WildcardEntry;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author zhaooo3
 * @description: TODO
 * @date 4/21/23 2:05 PM
 */
public class Classpath {

    // The three different entries that make up the classpath
    private Entry bootstrapClasspath;
    private Entry extensionClasspath;
    private Entry userClasspath;

    // Constructor that initializes the bootstrap, extension, and user classpaths
    public Classpath(String jreOption, String cpOption) {
        // Initialize the bootstrap and extension classpaths
        // "C:\Program Files\Java\jdk1.8.0_161\jre"
        bootstrapAndExtensionClasspath(jreOption);
        // Initialize the user-defined classpath
        // E:\..\org\itstack\demo\test\HelloWorld
        parseUserClasspath(cpOption);
    }

    private void bootstrapAndExtensionClasspath(String jreOption) {
        String jreDir = getJreDir(jreOption);

        //..jre/lib/*
        String jreLibPath = Paths.get(jreDir, "lib") + File.separator + "*";
        bootstrapClasspath = new WildcardEntry(jreLibPath);

        //..jre/lib/ext/*
        String jreExtPath = Paths.get(jreDir, "lib", "ext") + File.separator + "*";
        extensionClasspath = new WildcardEntry(jreExtPath);

    }

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

    private void parseUserClasspath(String cpOption) {
        if (cpOption == null) {
            cpOption = ".";
        }
        // create path Entry of current dir or cpOption
        userClasspath = Entry.create(cpOption);
    }

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
