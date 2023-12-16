package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

import java.util.Arrays;

/**
 * @description:
 * 用于表示 CONSTANT_Utf8_info 结构：
 * CONSTANT_Utf8_info {
 *     u1 tag;
 *     u2 length;
 *     // MUTF-8(Modified UTF-8 or Java UTF-8)编码的字符串
 *     u1 bytes[length];
 * }
 * @date 4/23/23 6:17 PM
 * @author zhaooo3
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
