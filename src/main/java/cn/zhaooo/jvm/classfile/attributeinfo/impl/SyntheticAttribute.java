package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description:
 * 如果一个类成员没有在源文件中出现，则必须标记带有Synthetic属性，或者设置ACC_SYNTHETIC标志。
 * Synthetic_attribute {
 *  u2 attribute_name_index;
 *  // 0
 *  u4 attribute_length;
 * }
 * @author zhaooo3
 * @date 4/26/23 8:13 PM
 */
public class SyntheticAttribute implements AttributeInfo {

    @Override
    public void readInfo(ClassReader reader) {
        //  read nothing
    }

}
