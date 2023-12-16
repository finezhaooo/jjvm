package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;
import cn.zhaooo.jvm.classfile.ConstantPool;

/**
 * @description:
 * 用于表示类或接口的 CONSTANT_Class_info 结构：
 * CONSTANT_Class_info{
 *     u1 tag;
 *     // 指向 CONSTANT_Utf8_info 常量的常量池索引
 *     u2 name_index;
 * }
 * @author zhaooo3
 * @date 4/23/23 6:36 PM
 */
public class ConstantClassInfo implements ConstantInfo {
    private ConstantPool constantPool;
    private int nameIdx;

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

    public int getNameIdx() {
        return nameIdx;
    }

    public String getName() {
        return constantPool.getUTF8(nameIdx);
    }
}
