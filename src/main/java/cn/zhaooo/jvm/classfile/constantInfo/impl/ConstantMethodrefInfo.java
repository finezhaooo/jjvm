package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;
import cn.zhaooo.jvm.classfile.ConstantPool;

import java.util.Map;

/**
 * @description:
 * 字段、方法和接口方法都由类似的结构表示：
 * CONSTANT_Methodref_info {
 *      u1 tag;
 *      u2 class_index;
 *      u2 name_and_type_index;
 * }
 * @author zhaooo3
 * @date 4/23/23 11:30 PM
 */
public class ConstantMethodrefInfo implements ConstantInfo {

    private ConstantPool constantPool;
    private int classIdx;
    private int nameAndTypeIdx;

    public ConstantMethodrefInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        classIdx = reader.readU2();
        nameAndTypeIdx = reader.readU2();
    }

    @Override
    public int getTag() {
        return CONSTANT_METHODREF;
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
