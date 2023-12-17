package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.ConstantPool;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * 在 Java 中，内部类可以嵌套在其他类或方法中。如果内部类是在某个方法内部定义的，那么它就被称为局部内部类。EnclosingMethod 属性就是用来描述这种情况的。
 * EnclosingMethod 属性存储了内部类在哪个方法中定义的信息，包括封闭类的名称和封闭方法的名称和描述符。这有助于确定内部类的上下文。
 * EnclosingMethod_attribute {
 *   // 该索引处存放CONSTANT_Utf8_info常量项，描述字符串“EnclosingMethod”。
 *   u2 attribute_name_index;
 *   // 4个字节
 *   u4 attribute_length;
 *   // CONSTANT_Class_info结构，表示最内层包含当前类声明的类。
 *   u2 class_index;
 *   // CONSTANT_NameAndType_info结构，表示由class_index属性所引用的类中的一个方法的名称和类型。
 *   // 如果当前类不是直接包含(enclose)在某个方法或构造器中，那么method_index项的值必须为0。
 *   u2 method_index;
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
