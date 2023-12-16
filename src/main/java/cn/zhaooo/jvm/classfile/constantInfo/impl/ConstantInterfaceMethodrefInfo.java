package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;
import cn.zhaooo.jvm.classfile.ConstantPool;

import java.util.Map;

/**
 * @description:
 * 字段、方法和接口方法都由类似的结构表示：
 * CONSTANT_InterfaceMethodref_info {
 *      u1 tag;
 *      // 指向 CONSTANT_Class_info 常量的常量池索引
 *      // 表示该接口所属的类或接口。
 *      u2 class_index;
 *      // 指向 CONSTANT_NameAndType_info 常量的常量池索引
 *      // 含了接口的名称和描述符，描述了接口的标识信息。
 *      u2 name_and_type_index;
 * }
 * @author zhaooo3
 * @date 4/23/23 11:33 PM
 */
public class ConstantInterfaceMethodrefInfo implements ConstantInfo {

    private final ConstantPool constantPool;
    private int classIdx;
    private int nameAndTypeIdx;

    public ConstantInterfaceMethodrefInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        classIdx = reader.readU2();
        nameAndTypeIdx = reader.readU2();
    }

    @Override
    public int getTag() {
        return CONSTANT_INTERFACE_METHODREF;
    }

    public int getClassIdx() {
        return classIdx;
    }

    public int getNameAndTypeIdx() {
        return nameAndTypeIdx;
    }

    public String getClassName() {
        return constantPool.getClassName(classIdx);
    }

    public Map<String, String> getNameAndDescriptor() {
        return constantPool.getNameAndType(nameAndTypeIdx);
    }

}
