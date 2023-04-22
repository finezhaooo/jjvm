package cn.zhaooo.jvm.classpath.impl;

import cn.zhaooo.jvm.classpath.Entry;

import java.io.IOException;
import java.nio.file.*;

/**
 * @author zhaooo3
 * @description: Compressed package path
 * @date 4/21/23 2:01 PM
 */
public class ZipEntry implements Entry {

    private final Path absolutePath;

    public ZipEntry(String path) {
        // get absolutePath
        this.absolutePath = Paths.get(path).toAbsolutePath();
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        // get bytes of class from zip
        try (FileSystem zipFs = FileSystems.newFileSystem(absolutePath, null)) {
            return Files.readAllBytes(zipFs.getPath(className));
        }
    }

    @Override
    public String toString() {
        return this.absolutePath.toString();
    }

}
