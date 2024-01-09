package cn.zhaooo.web.execute;

import cn.zhaooo.jvm.ClassLoader;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: WebLoader
 * @Description: WebLoader
 * @Author: zhaooo
 * @Date: 2024/01/07 20:49
 */
public class WebLoader extends ClassLoader {

    private final ClassLoader parent;

    private final Map<String, byte[]> byteMap;

    public WebLoader(ClassLoader parent) {
        super(null);
        this.parent = parent;
        classMap = new HashMap<>();
        byteMap = new HashMap<>();
    }

    public Class loadClass(String className) {
        Class clazz = parent.loadClass(className);
        if (null != clazz) {
            return clazz;
        }
        clazz = classMap.get(className);
        if (null != clazz) {
            return clazz;
        }
        if (className.getBytes()[0] == '[') {
            return loadArrayClass(className);
        }
        try {
            return loadNonArrayClass(className);
        } catch (Exception e) {
            System.err.println("OnlineLoader load class error: " + className);
        }
        return null;
    }

    @Override
    protected byte[] getClassBytes(String className) {
        return byteMap.get(className);
    }

    public Map<String, byte[]> getByteMap() {
        return byteMap;
    }
}
