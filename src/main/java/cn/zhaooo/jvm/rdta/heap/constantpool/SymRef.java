package cn.zhaooo.jvm.rdta.heap.constantpool;

import cn.zhaooo.jvm.rdta.heap.methodarea.Class;

/**
 * @ClassName: SymRef
 * @Description: 符号引用
 * @Author: zhaooo
 * @Date: 2023/08/07 23:22
 */
public class SymRef {
    public RunTimeConstantPool runTimeConstantPool; //  运行时常量池
    public String className;                        //  类的完全限定名
    public Class resolvedClass;                     //  解析后的类

    /**
     * 解析类符号引用 将其转换为直接引用
     */
    public Class getResolvedClass() {
        if (this.resolvedClass == null) {
            resolvedClass = resolveClassRef();
        }
        return resolvedClass;
    }

    /**
     * 解析常量池里面的类引用
     */
    private Class resolveClassRef() {
        Class currentClass = runTimeConstantPool.getClazz();
        // 用当前类的加载器加载ref对应的类
        Class resolvedClass = currentClass.getLoader().loadClass(className);
        if (!resolvedClass.isAccessibleTo(currentClass)) {
            throw new RuntimeException("Class [" + currentClass + "] cannot access Class [" + resolvedClass + "]");
        }
        return resolvedClass;
    }
}
