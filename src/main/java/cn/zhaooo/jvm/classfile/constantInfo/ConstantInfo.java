package cn.zhaooo.jvm.classfile.constantInfo;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.ConstantPool;
import cn.zhaooo.jvm.classfile.constantInfo.impl.*;

/**
 * @author zhaooo3
 * @description: TODO
 * @date 4/23/23 5:50 PM
 */
public interface ConstantInfo {

    int CONSTANT_CLASS = 7;
    int CONSTANT_FIELDREF = 9;
    int CONSTANT_METHODREF = 10;
    int CONSTANT_INTERFACEMETHODREF = 11;
    int CONSTANT_STRING = 8;
    int CONSTANT_INTEGER = 3;
    int CONSTANT_FLOAT = 4;
    int CONSTANT_LONG = 5;
    int CONSTANT_DOUBLE = 6;
    int CONSTANT_NAMEANDTYPE = 12;
    int CONSTANT_UTF8 = 1;
    int CONSTANT_METHODHANDLE = 15;
    int CONSTANT_METHODTYPE = 16;
    int CONSTANT_INVOKEDYNAMIC = 18;

    /**
     * 读取常量
     */
    static ConstantInfo read(ClassReader reader, ConstantPool constantPool) {
        //  读取tag值
        int tag = reader.readU1();
        //  创建具体常量
        ConstantInfo constantInfo = create(tag, constantPool);
        //  读取常量信息
        constantInfo.readInfo(reader);
        return constantInfo;
    }

    /**
     * 根据tag值创建具体常量
     */
    static ConstantInfo create(int tag, ConstantPool constantPool) {
        switch (tag) {
            case CONSTANT_INTEGER:
                return new ConstantIntegerInfo();
            case CONSTANT_FLOAT:
                return new ConstantFloatInfo();
            case CONSTANT_LONG:
                return new ConstantLongInfo();
            case CONSTANT_DOUBLE:
                return new ConstantDoubleInfo();
            case CONSTANT_UTF8:
                return new ConstantUtf8Info();
            case CONSTANT_STRING:
                return new ConstantStringInfo(constantPool);
            case CONSTANT_CLASS:
                return new ConstantClassInfo(constantPool);
            case CONSTANT_FIELDREF:
                return new ConstantFieldrefInfo(constantPool);
            case CONSTANT_METHODREF:
                return new ConstantMethodrefInfo(constantPool);
            case CONSTANT_INTERFACEMETHODREF:
                return new ConstantInterfaceMethodrefInfo(constantPool);
            case CONSTANT_NAMEANDTYPE:
                return new ConstantNameAndTypeInfo();
            case CONSTANT_METHODTYPE:
                return new ConstantMethodTypeInfo();
            case CONSTANT_METHODHANDLE:
                return new ConstantMethodHandleInfo();
            case CONSTANT_INVOKEDYNAMIC:
                return new ConstantInvokeDynamicInfo();
            default:
                throw new ClassFormatError("constant pool tag");
        }
    }

    /**
     * 读取常量信息
     */
    void readInfo(ClassReader reader);

    int getTag();

}
