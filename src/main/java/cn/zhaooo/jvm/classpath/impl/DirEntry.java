package cn.zhaooo.jvm.classpath.impl;

import cn.zhaooo.jvm.classpath.Entry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zhaooo3
 * @description: Directory path
 * the bytecode is obtained by reading the file under the absolute path through the Files.readAllBytes method.
 * @date 4/21/23 1:58 PM
 */
public class DirEntry implements Entry {

    private final Path absolutePath;

    public DirEntry(String path) {
        // get absolutePath
        this.absolutePath = Paths.get(path).toAbsolutePath();
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        return Files.readAllBytes(absolutePath.resolve(className));
    }

    @Override
    public String toString() {
        return this.absolutePath.toString();
    }
}
