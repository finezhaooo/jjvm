package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @description:
 * 用于表示字段或方法的 CONSTANT_NameAndType_info 结构，
 * 而不指示它属于哪个类或接口类型：
 * CONSTANT_NameAndType_info {
 *      u1 tag;
 *      // 字段或方法的名称索引，指向 CONSTANT_Utf8_info 常量
 *      // name_index 要么表示特殊方法名 <init>,要么表示有效字段或方法的未限定名称
 *      // Java程序员无法显式地调用类或接口的类初始化方法 <clinit>,这个方法是由编译器生成的,故name_index不能指向 <clinit>
 *      u2 name_index;
 *      // 字段或方法的描述符索引，指向 CONSTANT_Utf8_info 常量
 *      u2 descriptor_index;
 * }
 * @author zhaooo3
 * @date 4/23/23 7:04 PM
 */
public class ConstantNameAndTypeInfo implements ConstantInfo {

    private int nameIdx;
    private int descIdx;

    @Override
    public void readInfo(ClassReader reader) {
        nameIdx = reader.readU2();
        descIdx = reader.readU2();
    }

    @Override
    public int getTag() {
        return CONSTANT_NAME_AND_TYPE;
    }

    public int getNameIdx() {
        return nameIdx;
    }

    public int getDescIdx() {
        return descIdx;
    }
}
