package cn.zhaooo.jvm.rdta.heap.constantpool;

import cn.zhaooo.jvm.classfile.ConstantPool;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;
import cn.zhaooo.jvm.classfile.constantInfo.impl.*;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;

/**
 * @ClassName: RunTimeConstantPool
 * @Description: 运行时常量池
 * 运行时常量池是每个类或接口在加载到JVM时创建的一块内存区域，它包含了从类文件中的常量池复制过来的静态常量和符号引用，以及运行期间动态生成的常量
 * 运行时常量池是和类或接口一一对应的，每个类或接口都有自己的运行时常量池。
 * @Author: zhaooo
 * @Date: 2023/08/07 19:38
 */
public class RunTimeConstantPool {
    // class文件对应的类
    private Class clazz;
    // 保存的是常量数据，使用java.lang.Object保存
    private final Object[] constants;

    /**
     * 将class文件中的常量池转换成运行时常量池
     */
    public RunTimeConstantPool(Class clazz, ConstantPool constantPool) {
        ConstantInfo[] constantInfos = constantPool.getConstantInfos();
        int cpCount = constantInfos.length;

        this.clazz = clazz;
        constants = new Object[cpCount];

        for (int i = 1; i < cpCount; i++) {
            ConstantInfo constantInfo = constantInfos[i];

            switch (constantInfo.getTag()) {
                // Constant
                case ConstantInfo.CONSTANT_INTEGER:
                    ConstantIntegerInfo integerInfo = (ConstantIntegerInfo) constantInfo;
                    constants[i] = integerInfo.getVal();
                    break;

                case ConstantInfo.CONSTANT_FLOAT:
                    ConstantFloatInfo floatInfo = (ConstantFloatInfo) constantInfo;
                    constants[i] = floatInfo.getVal();
                    break;

                case ConstantInfo.CONSTANT_LONG:
                    ConstantLongInfo longInfo = (ConstantLongInfo) constantInfo;
                    constants[i] = longInfo.getVal();
                    i++;
                    break;

                case ConstantInfo.CONSTANT_DOUBLE:
                    ConstantDoubleInfo doubleInfo = (ConstantDoubleInfo) constantInfo;
                    constants[i] = doubleInfo.getVal();
                    i++;
                    break;

                case ConstantInfo.CONSTANT_STRING:
                    ConstantStringInfo stringInfo = (ConstantStringInfo) constantInfo;
                    constants[i] = stringInfo.getVal();
                    break;
                // SymRef
                case ConstantInfo.CONSTANT_CLASS:
                    constants[i] = ClassRef.create(this, (ConstantClassInfo) constantInfo);
                    break;

                case ConstantInfo.CONSTANT_FIELDREF:
                    constants[i] = FieldRef.create(this, (ConstantFieldrefInfo) constantInfo);
                    break;

                case ConstantInfo.CONSTANT_METHODREF:
                    constants[i] = MethodRef.create(this, (ConstantMethodrefInfo) constantInfo);
                    break;

                case ConstantInfo.CONSTANT_INTERFACE_METHODREF:
                    constants[i] = InterfaceMethodRef.create(this, (ConstantInterfaceMethodrefInfo) constantInfo);
                    break;

                default:
            }
        }
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    /**
     * 根据索引返回常量
     */
    public Object getConstants(int idx) {
        return constants[idx];
    }
}
