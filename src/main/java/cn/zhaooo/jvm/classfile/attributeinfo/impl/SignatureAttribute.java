package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.ConstantPool;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description:
 * 签名（Signature)用来编码以Java语言所写的声明，这些声明使用了Java虚拟机类型系统之外的类型。在只能访问class文件的情况下，签名有助于实现反射、调试及编译。
 * 在Java语言中，任何类、接口、构造器方法或字段的声明如果包含了类型变量(type variable)或参数化类型(parameterized type),
 * 则Signature属性会为它记录泛型签名信息
 * Signature_attribute {
 *  u2 attribute_name_index;
 *  u4 attribute_length;
 *  u2 signature_index;
 * }
 * @author zhaooo3
 * @date 4/26/23 8:18 PM
 */
public class SignatureAttribute implements AttributeInfo {

    private ConstantPool constantPool;
    private int signatureIdx;

    public SignatureAttribute(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        signatureIdx = reader.readU2();
    }

    public String getSignature() {
        return constantPool.getUTF8(signatureIdx);
    }

}
