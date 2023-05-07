package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @description:
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
