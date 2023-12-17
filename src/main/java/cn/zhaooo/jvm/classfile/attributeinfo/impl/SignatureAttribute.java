package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.ConstantPool;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description:
 * Signature属性在JDK 5增加到Class文件规范之中，它是一个可选的定长属性，可以出现于类、字段表和方法表结构的属性表中。
 * 在JDK 5里面大幅增强了Java语言的语法，在此之后，任何类、接口、初始化方法或成员的泛型签名如果包含了
 * 类型变量（Type Variable）或参数化类型（ParameterizedType），则Signature属性会为它记录泛型签名信息。
 * Signature_attribute {
 *   u2 attribute_name_index;
 *   u4 attribute_length;
 *   u2 signature_index;
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
