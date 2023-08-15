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
    public Class clazz;                             //  解析后的类

    /**
     * 解析类符号引用 将其转换为直接引用
     */
    public Class resolvedClass() {
        if (null != clazz) {
            return clazz;
        }
        //
        Class d = runTimeConstantPool.getClazz();
        Class c = d.loader.loadClass(className);
        clazz = c;
        return clazz;
    }
}
