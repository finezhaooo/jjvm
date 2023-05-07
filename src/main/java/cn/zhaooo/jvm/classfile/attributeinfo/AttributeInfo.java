package cn.zhaooo.jvm.classfile.attributeinfo;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.ConstantPool;
import cn.zhaooo.jvm.classfile.attributeinfo.impl.*;

/**
 * @description:
 *
 * @author zhaooo3
 * @date 4/26/23 1:36 PM
 */
public interface AttributeInfo {

    static AttributeInfo[] read(ClassReader reader, ConstantPool constantPool) {
        int attributesCount = reader.readU2();
        AttributeInfo[] attributes = new AttributeInfo[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            attributes[i] = readAttribute(reader, constantPool);
        }
        return attributes;
    }

    static AttributeInfo readAttribute(ClassReader reader, ConstantPool constantPool) {
        //  读取属性名索引
        int attrNameIdx = reader.readU2();
        //  从常量池中找到属性名
        String attrName = constantPool.getUTF8(attrNameIdx);
        //  读取属性长度
        int attrLen = reader.readU4ToInteger();
        //  创建具体属性实例
        AttributeInfo attrInfo = create(attrName, attrLen, constantPool);
        attrInfo.readInfo(reader);
        return attrInfo;
    }

    /**
     * 根据属性名创建具体属性
     */
    static AttributeInfo create(String attrName, int attrLen, ConstantPool constantPool) {
        switch (attrName) {
            case "BootstrapMethods":
                return new BootstrapMethodsAttribute();
            case "Code":
                return new CodeAttribute(constantPool);
            case "ConstantValue":
                return new ConstantValueAttribute();
            case "Deprecated":
                return new DeprecatedAttribute();
            case "EnclosingMethod":
                return new EnclosingMethodAttribute(constantPool);
            case "Exceptions":
                return new ExceptionsAttribute();
            case "InnerClasses":
                return new InnerClassesAttribute();
            case "LineNumberTable":
                return new LineNumberTableAttribute();
            case "LocalVariableTable":
                return new LocalVariableTableAttribute();
            case "LocalVariableTypeTable":
                return new LocalVariableTypeTableAttribute();
            //case "MethodParameters":
            //case "RuntimeInvisibleAnnotations":
            //case "RuntimeInvisibleParameterAnnotations":
            //case "RuntimeInvisibleTypeAnnotations":
            //case "RuntimeVisibleAnnotations":
            //case "RuntimeVisibleParameterAnnotations":
            //case "RuntimeVisibleTypeAnnotations":
            case "Signature":
                return new SignatureAttribute(constantPool);
            case "SourceFile":
                return new SourceFileAttribute(constantPool);
            //case "SourceDebugExtension":
            //case "StackMapTable":
            case "Synthetic":
                return new SyntheticAttribute();
            default:
                return new UnparsedAttribute(attrName, attrLen);
        }
    }

    /**
     * 读取属性信息
     */
    void readInfo(ClassReader reader);
}
