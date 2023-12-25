package cn.zhaooo.jvm.rdta.heap.util;

import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Method;

/**
 * @ClassName: MethodLookup
 * @Description: 查找方法
 * @Author: zhaooo
 * @Date: 2023/08/07 23:45
 */
public class MethodLookup {

    /**
     * 从类的继承中查找方法
     */
    static public Method lookupMethodInClass(Class clazz, String name, String descriptor) {
        for (Class c = clazz; c != null; c = c.getSuperClass()) {
            for (Method method : c.getMethods()) {
                if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 从类的接口中查找方法
     */
    static public Method lookupMethodInInterfaces(Class[] ifaces, String name, String descriptor) {
        for (Class inface : ifaces) {
            for (Method method : inface.getMethods()) {
                if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
                    return method;
                }
            }
        }
        return null;
    }
}