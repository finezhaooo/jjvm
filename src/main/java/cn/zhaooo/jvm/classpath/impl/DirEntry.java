package cn.zhaooo.jvm.classpath.impl;

import cn.zhaooo.jvm.classpath.Entry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zhaooo3
 * @description: 目录路径，该目录下只有.class文件，通常是编译后的输出文件夹
 * 通过使用 Files.readAllBytes 方法读取绝对路径下的文件，获得字节码。
 * @date 4/21/23 1:58 PM
 */
public class DirEntry implements Entry {

    // classFile所在的绝对路径
    private final Path absolutePath;

    public DirEntry(String path) {
        // get absolutePath
        this.absolutePath = Paths.get(path).toAbsolutePath();
    }

    @Override
    public byte[] readClass(String classFileName) throws IOException {
        return Files.readAllBytes(absolutePath.resolve(classFileName));
    }

    @Override
    public String toString() {
        return this.absolutePath.toString();
    }
}
