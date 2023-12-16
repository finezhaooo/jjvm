package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @description:
 * 用于表示 long 类型常量的 CONSTANT_Long_info 结构：
 * CONSTANT_Long_info{
 *      u1 tag;
 *      u4 high_bytes;
 *      u4 low_bytes;
 * }
 * ((long) high_bytes << 32) | (low_bytes & 0xFFFFFFFFL)
 * 在类文件的常量池表中，所有 8 字节的常量占用两个表成员（项）的空间。
 * 如果在常量池表中 CONSTANT_Long_info 或 CONSTANT_Double_info 结构项的索引为 n，
 * 则常量池表中下一个可用项的索引为 n+2。此时，常量池表中索引为 n+1 的项仍然有效，但必须视为不可用。
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
