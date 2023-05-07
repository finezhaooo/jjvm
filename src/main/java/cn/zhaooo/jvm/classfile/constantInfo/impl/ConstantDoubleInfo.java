package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @description:
 * CONSTANT_Double_info{
 *      u1 tag;
 *      // Uses 8 bytes to store IEEE754 double-precision floating-point numbers
 *      u4 high_bytes;
 *      u4 low_bytes;
 * }
 * @author zhaooo3
 * @date 4/23/23 6:50 PM
 */
public class ConstantDoubleInfo implements ConstantInfo {

    private double val;

    @Override
    public void readInfo(ClassReader reader) {
        val = reader.readU8ToDouble();
    }

    @Override
    public int getTag() {
        return CONSTANT_DOUBLE;
    }

    public double getVal() {
        return val;
    }
}
