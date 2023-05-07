package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @description:
 * The CONSTANT_NameAndType_info structure is used to represent a field or method,
 * without indicating which class or interface type it belongs to:
 * CONSTANT_NameAndType_info {
 *      u1 tag;
 *      // Field or method name index, pointing to CONSTANT_Utf8_info constant
 *      // This name_index either represents the special method name <init>, or represents the unqualified name of a valid field or method.
 *      u2 name_index;
 *      // Field or method descriptor index, pointing to the CONSTANT_Utf8_info constant
 *      u2 descriptor_index;
 * }
 * @author zhaooo3
 * @date 4/23/23 7:04 PM
 */
public class ConstantNameAndTypeInfo implements ConstantInfo {

    public int nameIdx;
    public int descIdx;

    @Override
    public void readInfo(ClassReader reader) {
        nameIdx = reader.readU2();
        descIdx = reader.readU2();
    }

    @Override
    public int getTag() {
        return CONSTANT_NAMEANDTYPE;
    }

}
