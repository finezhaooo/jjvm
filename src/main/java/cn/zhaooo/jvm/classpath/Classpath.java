package cn.zhaooo.jvm.classpath;

import cn.zhaooo.jvm.classpath.impl.WildcardEntry;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author zhaooo3
 * @description: Classpath（类路径）是Java运行时环境用于查找类文件或其他资源文件的路径。
 * 当Java应用程序启动时，Java虚拟机（JVM）会根据类路径来定位所需的类文件，这样它就能加载类并执行程序。
 * 类路径可以包含多个目录、JAR文件或ZIP文件，多个路径之间使用分号（Windows）或冒号（Unix/Linux）进行分隔。
 * @date 4/21/23 2:05 PM
 */
public class Classpath {

    // 启动类路径 ..jre/lib/*
    // 启动类路径用于存放Java的核心类库，包括java.lang、java.util等，它是在Java虚拟机启动时首先被搜索的路径。这里包含了JVM运行所需的基础类。
    // 在典型的JDK安装中，启动类路径可能包括rt.jar等JAR文件，这些文件包含了Java核心类库的实现。
    private Entry bootstrapClasspath;
    //  扩展类路径 ..jre/lib/ext/*
    //  扩展类路径用于存放Java的扩展类库，这些类库不属于核心类库，但对于某些应用程序是必需的。它是在启动类路径之后被搜索的路径。
    //  在典型的JDK安装中，扩展类路径可能包括ext目录下的JAR文件，这些文件包含了一些扩展的Java类库。
    private Entry extensionClasspath;
    //  用户类路径
    //  用户类路径是由用户指定的路径，用于存放用户自己编写的Java类。这是在启动类路径和扩展类路径之后被搜索的路径。用户可以在这里指定他们的应用程序代码的位置。
    //  如果用户在命令行中使用了-cp或-classpath选项来指定类路径，那么该路径就是用户类路径。例如，java -cp /path/to/classes MyApp。
    private Entry userClasspath;

    /**
     * -Xjre选项解析启动类路径和扩展类路径，-classpath/-cp选项解析用户类路径
     */
    public Classpath(String jreOption, String cpOption) {
        // Initialize the bootstrap and extension classpaths
        // -Xjre "C:\Program Files\Java\jdk8u392-b08\jre"
        bootstrapAndExtensionClasspath(jreOption);
        // Initialize the user-defined classpath
        // -classpath "C:\Program Files\Java\jdk8u392-b08\jre\lib\charsets.jar;C:\Program Files\Java\jdk8u392-b08\jre\lib\ext\access-bridge-64.jar;...（其他类路径，可以包含maven等引用的三方库）..."
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
        // 如果提供了jreOption并且路径存在，则使用提供的路径
        if (jreOption != null && Files.exists(Paths.get(jreOption))) {
            return jreOption;
        }

        // 如果jreOption为null或不包含jre，则检查当前目录是否存在jre文件夹
        if (Files.exists(Paths.get("./jre"))) {
            return "./jre";
        }

        // 如果当前目录没有jre文件夹，则检查系统环境变量JAVA_HOME
        String jh = System.getenv("JAVA_HOME");
        if (jh != null) {
            return Paths.get(jh, "jre").toString();
        }

        // 如果系统环境变量中没有名为JAVA_HOME的变量，则抛出异常
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
        // cpOption可以是文件，文件夹，多个文件夹，zip；所以使用Entry.create()
        // create path Entry of current dir or cpOption
        userClasspath = Entry.create(cpOption);
    }

    /**
     * 依次从启动类路径、扩展类路径和用户类路径中搜索class文件并读取
     * 最终都会调用DirEntry或者ZipEntry的readClass方法
     * 模拟双亲委派模型：因为jjvm的ClassLoader没有parent，故不能调用parent的loadClass方法。只能在loadClass调用readClass时，模拟双亲委派模型。
     */
    public byte[] readClass(String className) throws Exception {
        // 将类名转换为文件名
        className = className + ".class";

        // 在启动类路径中查找类
        try {
            return bootstrapClasspath.readClass(className);
        } catch (Exception ignored) {
            // 如果在启动类路径中未找到类，忽略并继续搜索
        }

        // 在扩展类路径中查找类
        try {
            return extensionClasspath.readClass(className);
        } catch (Exception ignored) {
            // 忽略
        }

        // 在用户类路径中查找类
        return userClasspath.readClass(className);
    }
}
