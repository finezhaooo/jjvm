package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @author zhaooo3
 * @description:
 * CONSTANT_Utf8-info{
 *     u1 tag;
 *     u2 length;
 *     u1 bytes[length]; // MUTF-8(Modified UTF8 or Java UTF-8)
 * }
 * @date 4/23/23 6:17 PM
 */
public class ConstantUtf8Info implements ConstantInfo {

    private String str;

    @Override
    public void readInfo(ClassReader reader) {
        int length = reader.readU2();
        byte[] bytes = reader.readBytes(length);
        str = new String(bytes);
    }

    @Override
    public int getTag() {
        return CONSTANT_UTF8;
    }

    public String getStr() {
        return str;
    }
}
