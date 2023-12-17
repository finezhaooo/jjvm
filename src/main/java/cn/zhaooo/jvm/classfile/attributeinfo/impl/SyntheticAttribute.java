package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description:
 * Synthetic属性代表此字段或者方法并不是由Java源码直接产生的，而是由编译器自行添加的，在JDK 5之后，标识一个类、字段或者方法是编译器自动产生的，
 * 也可以设置它们访问标志中的ACC_SYNTHETIC标志位。
 * 所有由不属于用户代码产生的类、方法及字段都应当至少设置Synthetic属性或者ACC_SYNTHETIC标志位中的一项，
 * 唯一的例外是实例构造器“<init>()”方法和类构造器“<clinit>()”方法。
 * Synthetic_attribute {
 *   u2 attribute_name_index;
 *   // 0
 *   u4 attribute_length;
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
