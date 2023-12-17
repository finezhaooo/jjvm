package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description:
 * 对非static类型的变量（也就是实例变量）的赋值是在实例构造器<init>()方法中进行的；
 * 而对于类变量，则有两种方式可以选择：在类构造器<clinit>()方法中或者使用ConstantValue属性。
 * 目前Oracle公司实现的Javac编译器的选择是，如果同时使用final和static来修饰一个变量（按照习惯，这里称“常量”更贴切），
 * 并且这个变量的数据类型是基本类型或者java.lang.String的话，就将会生成ConstantValue属性来进行初始化；
 * 如果这个变量没有被final修饰，或者并非基本类型及字符串，则将会选择在<clinit>()方法中进行初始化。
 * ConstantValue_attribute {
 *   u2 attribute_name_index;
 *   // u4类型整数，固定值为2，表示ConstantValue_attribute属性项的实际内容占用2个字节的空间，也即constantvalue_index所占用的字节数。
 *   u4 attribute_length;
 *   // constantvalue_index数据项代表了常量池中一个字面量常量的引用
 *   // 该索引处可以存放CONSTANT_Long、CONSTANT_Integer、CONSTANT_Float、CONSTANT_Double、CONSTANT_String常量项
 *   u2 constantvalue_index;
 * }
 * @author zhaooo3
 * @date 4/26/23 2:44 PM
 */
public class ConstantValueAttribute implements AttributeInfo {

    //  常量池索引，具体指向哪种常量因字段类型而异
    private int constantValueIdx;

    @Override
    public void readInfo(ClassReader reader) {
        constantValueIdx = reader.readU2();
    }

    public int getConstantValueIdx() {
        return constantValueIdx;
    }
}
