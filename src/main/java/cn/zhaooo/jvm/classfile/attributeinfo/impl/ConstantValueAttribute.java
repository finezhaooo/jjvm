package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description:
 * ConstantValue_attribute {
 *  u2 attribute_name_index;
 *  // u4类型整数，固定值为2，表示ConstantValue_attribute属性项的实际内容占用2个字节的空间，也即constantvalue_index所占用的字节数。
 *  u4 attribute_length;
 *  // 该索引处可以存放CONSTANT_Long、CONSTANT_Integer、CONSTANT_Float、CONSTANT_Double、CONSTANT_String常量项
 *  u2 constantvalue_index;
 * }
 * @author zhaooo3
 * @date 4/26/23 2:44 PM
 */
public class ConstantValueAttribute implements AttributeInfo {

    //  常量池索引，具体指向哪种常量因字段类型而异
    private int constantValueIdx;

    @Override
    public void readInfo(ClassReader reader) {
        constantValueIdx = reader.readU2();
    }

    public int getConstantValueIdx() {
        return constantValueIdx;
    }
}
