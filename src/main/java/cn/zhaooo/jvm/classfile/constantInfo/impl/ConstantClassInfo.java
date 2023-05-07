package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;
import cn.zhaooo.jvm.classfile.ConstantPool;

/**
 * @description:
 * The CONSTANT_Class_info structure is used to represent a class or an interface:
 * CONSTANT_Class_info{
 *     u1 tag;
 *     u2 name_index; // Constant pool index, pointing to the CONSTANT_Utf8_info constant
 * }
 * @author zhaooo3
 * @date 4/23/23 6:36 PM
 */
public class ConstantClassInfo implements ConstantInfo {
    public ConstantPool constantPool;
    public int nameIdx;

    public ConstantClassInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        nameIdx = reader.readU2();
    }

    @Override
    public int getTag() {
        return CONSTANT_CLASS;
    }

    public String getName() {
        return constantPool.getUTF8(nameIdx);
    }
}
