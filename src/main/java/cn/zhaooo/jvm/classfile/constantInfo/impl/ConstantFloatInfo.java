package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @description:
 * CONSTANT_Float_info{
 *      u1 tag;
 *      u4 bytes // 8 bytes for IEEE754 single-precision floating-point constants
 * }
 * @author zhaooo3
 * @date 4/23/23 6:42 PM
 */
public class ConstantFloatInfo implements ConstantInfo {

    private float val;

    @Override
    public void readInfo(ClassReader reader) {
        val = reader.readU8ToFloat();
    }

    @Override
    public int getTag() {
        return CONSTANT_FLOAT;
    }

    public float getVal() {
        return val;
    }
}
