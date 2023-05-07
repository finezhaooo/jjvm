package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @description:
 * The CONSTANT_MethodHandle_info structure is used to represent a method handle:
 * CONSTANT_MethodHandle_info {
 *      u1 tag;
 *      // The value of the reference kind item must be in the range of 1~9 (including 1 and 9),
 *      // which indicates the method handle The type (kind).
 *      // The method handle type determines the bytecode behavior of the handle
 *      u1 reference_kind;
 *      // must be valid index to constant pool table
 *      u2 reference_index;
 * }
 * @author zhaooo3
 * @date 4/24/23 12:09 AM
 */
public class ConstantMethodHandleInfo implements ConstantInfo {

    private int referenceKind;
    private int referenceIdx;

    @Override
    public void readInfo(ClassReader reader) {
        referenceKind = reader.readU1();
        referenceIdx = reader.readU2();
    }

    @Override
    public int getTag() {
        return CONSTANT_METHODHANDLE;
    }

}
