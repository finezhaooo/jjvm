package cn.zhaooo.jvm.classpath;

import cn.zhaooo.jvm.classpath.impl.CompositeEntry;
import cn.zhaooo.jvm.classpath.impl.DirEntry;
import cn.zhaooo.jvm.classpath.impl.WildcardEntry;
import cn.zhaooo.jvm.classpath.impl.ZipEntry;

import java.io.File;
import java.io.IOException;

/**
 * @author zhaooo3
 * @description: Classpath interface
 * @date 4/21/23 1:56 PM
 */
public interface Entry {

    /**
     * 寻找并加载class文件
     * @param className class文件的相对路径，路径之间用斜线分割，文件名有.class后缀
     * Entry->className->+.class->readClass->bytes
     * @return class文件的字节数据
     */
    byte[] readClass(String className) throws IOException;

    // static method in interface
    // creat different Entry by different path
    static Entry create(String path) {

        // File.pathSeparator;(win\linux)
        if (path.contains(File.pathSeparator)) {
            return new CompositeEntry(path);
        }

        if (path.endsWith("*")) {
            return new WildcardEntry(path);
        }

        if (path.endsWith(".jar") || path.endsWith(".JAR") || path.endsWith(".zip") || path.endsWith(".ZIP")) {
            return new ZipEntry(path);
        }
        return new DirEntry(path);
    }
}
