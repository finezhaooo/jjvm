package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;
import cn.zhaooo.jvm.classfile.ConstantPool;

/**
 * @description:
 * The CONSTANT_String_info structure is used to represent constant objects of the type String:
 * CONSTANT_String_info{
 *      u1 tag;
 *      u2 string_index; //Index to CONSTANT_Utf8_info in the constant pool
 * }
 * @author zhaooo3
 * @date 4/23/23 6:57 PM
 */
public class ConstantStringInfo implements ConstantInfo {

    private ConstantPool constantPool;
    private int strIdx;

    public ConstantStringInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        strIdx = reader.readU2();
    }

    @Override
    public int getTag() {
        return this.CONSTANT_STRING;
    }

    /**
     * 按索引从常量池中查找字符串
     */
    public String getString() {
        return constantPool.getUTF8(strIdx);
    }
}
