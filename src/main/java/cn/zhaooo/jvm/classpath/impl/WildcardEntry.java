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
    // Method to generate the list of paths from the wildcard path
    private static String toPathList(String wildcardPath) {
        // Remove "*" from the wildcard path
        String baseDir = wildcardPath.replace("*", "");
        try {
            // Use Files.walk to recursively traverse the base directory and its subdirectories
            return Files.walk(Paths.get(baseDir))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(p -> p.endsWith(".jar") || p.endsWith(".JAR"))
                    // Join the paths using the File.pathSeparator
                    .collect(Collectors.joining(File.pathSeparator));
        } catch (IOException e) {
            return "";
        }
    }

}