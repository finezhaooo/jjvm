package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @description:
 * 编译时期的常量，编译器会在字节码文件的常量池中保存它的值为 ConstantIntegerInfo，以便在运行时被直接使用。
 * final修饰的int，或者不存在改变改int的方法，会保在ConstantIntegerInfo中
 * 普通的实例字段，以字段的形式保存在字段表中，不会出现在常量池中
 * CONSTANT_Interger_info{
 *      u1 tag;
 *      u4 bytes;
 * }
 * @author zhaooo3
 * @date 4/23/23 6:40 PM
 */
public class ConstantIntegerInfo implements ConstantInfo {

    private int val;

    @Override
    public void readInfo(ClassReader reader) {
        val = reader.readU4ToInteger();
    }

    @Override
    public int getTag() {
        return CONSTANT_INTEGER;
    }

    public int getVal() {
        return val;
    }
}
