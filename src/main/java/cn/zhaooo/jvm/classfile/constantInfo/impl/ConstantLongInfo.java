package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @description:
 * CONSTANT_Long_info{
 *      u1 tag;
 *      u4 high_bytes;
 *      u4 low_bytes;
 * }
 * ((long) high_bytes << 32) + low_bytes
 * In the constant pool table of the class file, all 8-byte constants occupy the space of two table members (items).
 * If the index of a CONSTANT_Long_info or CONSTANT_Double_info structure item in the constant pool table is n,
 * the index of the next available item in the constant pool table is n+2. At this time,
 * the item with index +1 in the constant pool table is still valid but must consider unavailable.
 * @author zhaooo3
 * @date 4/23/23 6:48 PM
 */
public class ConstantLongInfo implements ConstantInfo {

    private long val;

    @Override
    public void readInfo(ClassReader reader) {
        val = reader.readU8ToLong();
    }

    @Override
    public int getTag() {
        return CONSTANT_LONG;
    }

    public long getVal() {
        return val;
    }
}
