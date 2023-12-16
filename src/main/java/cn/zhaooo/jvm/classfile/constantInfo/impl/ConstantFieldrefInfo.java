package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;
import cn.zhaooo.jvm.classfile.ConstantPool;

import java.util.Map;

/**
 * @description:
 * 字段、方法和接口方法都由类似的结构表示：
 * CONSTANT_Fieldref_info {
 *      u1 tag;
 *      // 指向 CONSTANT_Class_info 常量的常量池索引
 *      // 表示该字段所属的类或接口。
 *      u2 class_index;
 *      // 指向 CONSTANT_NameAndType_info 常量的常量池索引
 *      // 含了字段的名称和描述符，描述了字段的标识信息。
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
