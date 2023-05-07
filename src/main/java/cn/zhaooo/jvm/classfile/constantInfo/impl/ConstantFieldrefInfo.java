package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;
import cn.zhaooo.jvm.classfile.ConstantPool;

import java.util.Map;

/**
 * @description:
 * Fields, methods, and interface methods are represented by similar structures:
 * CONSTANT_Fieldref_info {
 *      u1 tag;
 *      // Constant pool index, pointing to the CONSTANT_Class_info constant
 *      // The current field or method is a member of this class or interface
 *      u2 class_index;
 *      // Constant pool index, pointing to the CONSTANT_NameAndType_info constant
 *      // The name and descriptor of the current field or method
 *      u2 name_and_type_index;
 * }
 * @author zhaooo3
 * @date 4/23/23 11:24 PM
 */
public class ConstantFieldrefInfo implements ConstantInfo {

    private ConstantPool constantPool;
    private int classIdx;
    private int nameAndTypeIdx;

    public ConstantFieldrefInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        classIdx = reader.readU2();
        nameAndTypeIdx = reader.readU2();
    }

    @Override
    public int getTag() {
        return CONSTANT_FIELDREF;
    }

    public String getClassName() {
        return constantPool.getClassName(classIdx);
    }

    public Map<String, String> getNameAndDescriptor() {
        return constantPool.getNameAndType(nameAndTypeIdx);
    }

}
