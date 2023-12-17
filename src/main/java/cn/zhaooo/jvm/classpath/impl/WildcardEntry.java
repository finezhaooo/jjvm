package cn.zhaooo.jvm.classpath.impl;

import cn.zhaooo.jvm.classpath.Entry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhaooo3
 * @description: 通配符类型的路径
 * @date 4/21/23 1:58 PM
 */

public class WildcardEntry extends CompositeEntry {

    public WildcardEntry(String path) {
        // 使用通配符路径生成的路径列表调用 CompositeEntry 构造函数
        super(toPathList(path));
    }

    /**
     * 获取通配符路径下的所有.jar/.JAR文件并转变为由pathSeparator连接的String
     * jre/lib/* 和 jre/lib/ext/* 下包含的源码都是打包为jar
     */
    private static String toPathList(String wildcardPath) {
        // 从通配符路径中移除 "*"
        String baseDir = wildcardPath.replace("*", "");
        // 使用 Files.walk 递归遍历基目录及其子目录
        // 使用 try-with-resources 来确保资源自动关闭
        try (Stream<Path> pathStream = Files.walk(Paths.get(baseDir))) {
            return pathStream
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    // 通配符类路径不能递归匹配子目录下的 JAR 文件
                    .filter(p -> p.endsWith(".jar") || p.endsWith(".JAR"))
                    // 使用 File.pathSeparator 连接路径
                    .collect(Collectors.joining(File.pathSeparator));
        } catch (IOException e) {
            return "";
        }
    }
}