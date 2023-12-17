package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description:
 * Exceptions属性的作用是列举出方法中可能抛出的受查异常（Checked Excepitons），也就是方法描述时在throws关键字后面列举的异常。
 *h
 * Exceptions_attribute {
 *   u2 attribute_name_index;
 *   u4 attribute_length;
 *   u2 number_of_exceptions;
 *   // 指向CONSTANT_Class_info的索引表，表示这个方法声明要抛出的异常所属的类的类型。
 *   u2 exception_index_table[number_of_exceptions];
 * }
 * @author zhaooo3
 * @date 4/26/23 4:01 PM
 */
public class ExceptionsAttribute implements AttributeInfo {

    private int[] exceptionIndexTable;

    @Override
    public void readInfo(ClassReader reader) {
        exceptionIndexTable = reader.readU2s();
    }

    public int[] getExceptionIndexTable() {
        return exceptionIndexTable;
    }

}
