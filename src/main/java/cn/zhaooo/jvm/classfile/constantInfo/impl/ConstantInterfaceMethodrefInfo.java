package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;
import cn.zhaooo.jvm.classfile.ConstantPool;

import java.util.Map;

/**
 * @description:
 * Fields, methods, and interface methods are represented by similar structures:
 * CONSTANT_InterfaceMethodref_info {
 *      u1 tag;
 *      u2 class_index;
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
        return CONSTANT_INTERFACEMETHODREF;
    }

    public String getClassName() {
        return constantPool.getClassName(classIdx);
    }

    public Map<String, String> getNameAndDescriptor() {
        return constantPool.getNameAndType(nameAndTypeIdx);
    }

}
