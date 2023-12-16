package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;
import cn.zhaooo.jvm.classfile.ConstantPool;

/**
 * @description:
 * 用于表示 String 类型常量对象的 CONSTANT_String_info 结构：
 * CONSTANT_String_info{
 *      u1 tag;
 *      // 指向常量池中的 CONSTANT_Utf8_info 的索引
 *      u2 string_index;
 * }
 * CONSTANT_Utf8_info 存储了实际的字符串内容，而 CONSTANT_String_info 则引用了这个内容，并通过 string_index 字段建立了常量池中字符串常量的索引关系。
 * 这种分离使得字符串常量可以被共享，多个类可以引用同一份字符串内容，从而节省内存。
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

    public int getStrIdx() {
        return strIdx;
    }

    public String getVal() {
        return constantPool.getUTF8(strIdx);
    }
}
