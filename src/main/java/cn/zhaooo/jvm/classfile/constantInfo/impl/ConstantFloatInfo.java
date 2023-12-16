package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @description:
 * 用于表示 IEEE754 单精度浮点数的 CONSTANT_Float_info 结构：
 * CONSTANT_Float_info{
 *      u1 tag;
 *      // 使用 8 字节存储 IEEE754 单精度浮点数常量
 *      u4 bytes
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
