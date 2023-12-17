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
 * 对bytecode所在位置即类路径的抽象，使得可以从path中通过className找到对应的字节码和通过path创建不同的Entry
 * @date 4/21/23 1:56 PM
 */
public interface Entry {

    /**
     * 找并加载class文件
     * classpath调用的转化字节码为字节的具体方法
     * @param classFileName class文件的相对路径，路径之间用斜线分割，文件名有.class后缀
     * @return class文件的字节数据
     */
    byte[] readClass(String classFileName) throws IOException;

    // static method in interface
    // creat different Entry by different path
    // 递归调用创建
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
