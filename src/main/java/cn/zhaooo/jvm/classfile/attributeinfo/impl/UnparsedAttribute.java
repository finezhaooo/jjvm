package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description: 解析失败的默认属性
 * @author zhaooo3
 * @date 5/4/23 11:32 AM
 */
public class UnparsedAttribute implements AttributeInfo {

    private String name;
    private int length;
    private byte[] info;

    public UnparsedAttribute(String attrName, int attrLen) {
        name = attrName;
        length = attrLen;
    }

    @Override
    public void readInfo(ClassReader reader) {
        info = reader.readBytes(length);
    }

    public byte[] getInfo() {
        return info;
    }
}