package cn.zhaooo.jvm.rdta.heap.constantpool;

import cn.zhaooo.jvm.classfile.constantInfo.impl.ConstantClassInfo;

/**
 * @ClassName: ClassRef
 * @Description: 类符号引用
 * @Author: zhaooo
 * @Date: 2023/08/07 23:41
 */
public class ClassRef extends SymRef {
    /**
     * 根据class文件中存储的类常量创建类符号引用
     */
    public static ClassRef create(RunTimeConstantPool runTimeConstantPool, ConstantClassInfo classInfo) {
        ClassRef ref = new ClassRef();
        ref.runTimeConstantPool = runTimeConstantPool;
        ref.className = classInfo.getName();
        return ref;
    }
}
