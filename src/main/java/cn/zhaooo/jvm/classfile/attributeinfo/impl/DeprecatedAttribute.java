package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description:
 * Deprecated属性是可选定长属性，位于ClassFile、field_info或method_info结构的属性表中。类、接口、方法或字段都可以带有 Deprecated属性。
 * 如果类、接口、方法或字段标记了此属性，则说明它将会在后续某个版本中被取代。
 * Deprecated_attribute {
 *   u2 attribute_name_index;
 *   u4 attribute_length;
 * }
 * @author zhaooo3
 * @date 5/4/23 11:20 AM
 */
public class DeprecatedAttribute implements AttributeInfo {

    @Override
    public void readInfo(ClassReader reader) {
        //  read nothing
    }
}
