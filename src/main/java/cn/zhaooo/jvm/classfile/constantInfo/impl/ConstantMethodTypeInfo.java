package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @description:
 * The CONSTANT_MethodType_info structure is used to represent a method type:
 * CONSTANT_MethodType_info {
 *      u1 tag;
 *      u2 descriptor_index;
 * }
 * @author zhaooo3
 * @date 4/24/23 12:13 AM
 */
public class ConstantMethodTypeInfo implements ConstantInfo {

    private int descriptorIdx;

    @Override
    public void readInfo(ClassReader reader) {
        descriptorIdx = reader.readU2();
    }

    @Override
    public int getTag() {
        return CONSTANT_METHODTYPE;
    }

}