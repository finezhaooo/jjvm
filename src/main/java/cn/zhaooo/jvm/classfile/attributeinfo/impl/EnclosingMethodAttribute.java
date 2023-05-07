package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.ConstantPool;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * EnclosingMethod属性是可选的定长属性，位于ClassFile结构的属性表中。当且仅当class为局部类或者匿名类时，才能具有该属性
 * EnclosingMethod_attribute {
 *  // 该索引处存放CONSTANT_Utf8_info常量项，描述字符串“EnclosingMethod”。
 *  u2 attribute_name_index;
 *  // 4个字节
 *  u4 attribute_length;
 *  u2 class_index;
 *  // 如果当前类不是直接包含(enclose)在某个方法或构造器中，那么method_index项的值必须为0。
 *  u2 method_index;
 * }
 * @author zhaooo3
 * @date 4/26/23 7:56 PM
 */
public class EnclosingMethodAttribute implements AttributeInfo {

    private ConstantPool constantPool;
    private int classIdx;
    private int methodIdx;

    public EnclosingMethodAttribute(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        classIdx = reader.readU2();
        methodIdx = reader.readU2();
    }

    public String getClassName() {
        return constantPool.getClassName(classIdx);
    }

    public Map<String, String> getMethodNameAndDescriptor() {
        if (methodIdx <= 0) {
            return new HashMap<>();
        }
        return constantPool.getNameAndType(methodIdx);
    }

}
