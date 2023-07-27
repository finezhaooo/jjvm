package cn.zhaooo.jvm.classpath.impl;

import cn.zhaooo.jvm.classpath.Entry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * @author zhaooo3
 * @description: Wildcard type path
 * @date 4/21/23 1:58 PM
 */
public class WildcardEntry extends CompositeEntry {

    public WildcardEntry(String path) {
        // Call CompositeEntry constructor with the list of paths generated from the wildcard path
        super(toPathList(path));
    }

    /**
     * 获取通配符路径下的所有.jar/.JAR文件并转变为由pathSeparator连接的String
     * jre/lib/* 和 jre/lib/ext/* 下包含的源码都是打包为jar
     */
    private static String toPathList(String wildcardPath) {
        // Remove "*" from the wildcard path
        String baseDir = wildcardPath.replace("*", "");
        try {
            // Use Files.walk to recursively traverse the base directory and its subdirectories
            return Files.walk(Paths.get(baseDir))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    // 通配符类路径不能递归匹配子目录下的JAR文件
                    .filter(p -> p.endsWith(".jar") || p.endsWith(".JAR"))
                    // Join the paths using the File.pathSeparator
                    .collect(Collectors.joining(File.pathSeparator));
        } catch (IOException e) {
            return "";
        }
    }
}