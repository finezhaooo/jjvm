package cn.zhaooo.web.compile;


import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.*;

/**
 * @ClassName: StringSourceCompiler
 * @Description: 源代码编译器
 * @Author: zhaooo
 * @Date: 2024/01/07 18:51
 */
public class StringSourceCompiler {
    private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public static Map<String, byte[]> compile(Map<String, String> source, DiagnosticCollector<JavaFileObject> compileCollector) {
        // 保存当前请求的所有源码文件
        Map<String, JavaFileObject> fileMap = new HashMap<>();
        JavaFileManager javaFileManager = new TmpJavaFileManager(compiler.getStandardFileManager(compileCollector, null, null), fileMap);
        source.forEach((name, s) -> {
            JavaFileObject sourceJavaFileObject = new TmpJavaFileObject(name, s);
            fileMap.put(name, sourceJavaFileObject);
        });
        Boolean result = compiler.getTask(null, javaFileManager, compileCollector,
                null, null, fileMap.values()).call();
        // 编译失败
        if (!result) {
            return null;
        }
        Map<String, byte[]> ret = new HashMap<>();
        for (String s : source.keySet()) {
            JavaFileObject bytesJavaFileObject = fileMap.get(s);
            ret.put(s, ((TmpJavaFileObject) bytesJavaFileObject).getCompiledBytes());
        }
        return ret;
    }

    /**
     * 管理JavaFileObject对象的工具
     */
    public static class TmpJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
        Map<String, JavaFileObject> fileMap;

        protected TmpJavaFileManager(JavaFileManager fileManager, Map<String, JavaFileObject> fileMap) {
            super(fileManager);
            this.fileMap = fileMap;
        }

        @Override
        public JavaFileObject getJavaFileForInput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind) throws IOException {
            JavaFileObject javaFileObject = fileMap.get(className);
            System.out.println(javaFileObject);
            if (javaFileObject == null) {
                return super.getJavaFileForInput(location, className, kind);
            }
            return javaFileObject;
        }

        @Override
        public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
            JavaFileObject javaFileObject = new TmpJavaFileObject(className, kind);
            fileMap.put(className, javaFileObject);
            return javaFileObject;
        }
    }

    /**
     * 用来封装表示源码与字节码的对象
     */
    public static class TmpJavaFileObject extends SimpleJavaFileObject {
        private final String source;
        private ByteArrayOutputStream outputStream;

        /**
         * 构造用来存储源代码的JavaFileObject
         * 需要传入源码source，然后调用父类的构造方法创建kind = Kind.SOURCE的JavaFileObject对象
         */
        public TmpJavaFileObject(String name, String source) {
            super(URI.create(name + Kind.SOURCE.extension), Kind.SOURCE);
            this.source = source;
        }

        /**
         * 构造用来存储字节码的JavaFileObject
         * 需要传入kind，即我们想要构建一个存储什么类型文件的JavaFileObject
         */
        public TmpJavaFileObject(String name, Kind kind) {
            super(URI.create(name + Kind.SOURCE.extension), kind);
            this.source = null;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            if (source == null) {
                throw new IllegalArgumentException("source == null");
            }
            return source;
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            outputStream = new ByteArrayOutputStream();
            return outputStream;
        }

        public byte[] getCompiledBytes() {
            return outputStream.toByteArray();
        }
    }
}

