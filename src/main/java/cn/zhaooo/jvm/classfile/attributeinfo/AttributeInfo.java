package cn.zhaooo.jvm.classfile.attributeinfo;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.ConstantPool;
import cn.zhaooo.jvm.classfile.attributeinfo.impl.*;

/**
 * @description:
 * AttributeInfo 是 Java class 文件中的一种结构，用于提供额外的元数据信息，以丰富类、字段或方法的定义。
 * 这些额外的信息可能包括编译器生成的信息、运行时需要的信息，以及其他一些与 Java 虚拟机和类加载器相关的细节。
 * attribute_info {
 *     u2 attribute_name_index;
 *     // attribute_length 字段的主要作用是为了标识整个 AttributeInfo 结构的长度，以便在解析字节码时能够正确地定位和跳过相应的字节。
 *     // 在具体的 info 中，实际的长度通常是通过具体的属性信息来确定的，而不是由 attribute_length 明确指定的。
 *     u4 attribute_length;
 *     u1 info[attribute_length];
 * }
 * 在JVM中，AttributeInfo 起到了以下作用：
 *  提供额外的元数据信息： AttributeInfo 结构用于存储与类、字段或方法相关的额外信息。这些信息不仅仅包括编译器生成的内容，还可以包含开发者定义的注解、调试信息等。
 *  支持类加载过程： 在类加载过程中，Java 虚拟机需要获取类的结构信息，包括字段、方法、接口等。AttributeInfo 提供了这些信息的扩展，使得虚拟机能够更灵活地加载和解析类文件。
 *  支持运行时特性： 一些 AttributeInfo 结构存储的信息在运行时才会被使用。例如，BootstrapMethods 属性用于在运行时支持 invokedynamic 指令，通过引导方法动态确定调用点的方法和方法类型。
 *  与调试相关： 一些调试信息也可以通过 AttributeInfo 存储，以支持调试器在运行时的调试操作。例如，LineNumberTable 用于映射字节码行号和源代码行号，方便调试时的源代码级别调试。
 *  与安全检查相关： Signature 属性用于存储泛型签名信息，对于泛型类型的类、字段或方法，这个信息在运行时的类型检查和反射操作中非常重要。
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
            // TODO MethodParameters
            //case "MethodParameters":
            // TODO Annotation支持
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
            // TODO SourceDebugExtension
            //case "SourceDebugExtension":
            // TODO StackMapTable
            //case "StackMapTable":
            case "Synthetic":
                return new SyntheticAttribute();
            default:
            // 解析属性失败
                return new UnparsedAttribute(attrName, attrLen);
        }
    }

    /**
     * 读取属性信息
     */
    void readInfo(ClassReader reader);
}
