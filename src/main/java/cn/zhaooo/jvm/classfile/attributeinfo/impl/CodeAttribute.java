package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.ConstantPool;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description:
 * Code属性是Class文件中最重要的一个属性，如果把一个Java程序中的信息分为代码（Code，方法体里面的Java代码）
 * 和元数据（Metadata，包括类、字段、方法定义及其他信息）两部分，那么在整个Class文件里，Code属性用于描述代码，所有的其他数据项目都用于描述元数据。
 *
 * Code_attribute {
 *  // 该索引处存放CONSTANT_Utf8_info常量项，描述字符串“Code”。
 *  u2 attribute_name_index;
 *  // u4类型整数，表示Code属性内容所占用的字节数，去掉前6个字节
 *  u4 attribute_length;
 *  // Code所属的方法在任意运行时刻的操作数栈的最大深度。
 *  u2 max_stack;
 *  // 描述分配在Code所属方法所引用的局部变量表中的局部变量个数，包括调用此方法时，传递参数的局部变量。
 *  // 由于long和double类型占用局部变量的两个槽位，因此long和double类型的局部变量的最大索引是max_locals-2，
 *  // 其他类型的局部变量的最大索引为max_locals-1。
 *  u2 max_locals;
 *  u4 code_length;
 *  u1 code[code_length];
 *  // Code属性所属方法的异常处理表，表中的每个元素为一个异常处理器结构项，表中元素之间的顺序不能随意更改
 *  u2 exception_table_length;
 *    // code[]数组的一个有效索引，标记异常触发区域的起始点。
 *  { u2 start_pc;
 *    // code[]数组的一个有效索引，标记异常处罚区域的结束点。
 *    u2 end_pc;
 *    // code[]数组的一个有效索引，标记在[start_pc,end_pc)区域内触发的异常所对应的异常处理器的起始点。
 *    u2 handler_pc;
 *    // 该索引处存放CONSTANT_Class_info常量项，描述当前异常处理器指定捕获的异常类型。
 *    // 只有当抛出的异常时指定异常类或者其子类的实例时，对应的异常处理器才会被调用。
 *    // 如果catch_type的值为0，则此异常处理器会在所有异常抛出时都被调用。以此可以实现finally语句。
 *    u2 catch_type;
 *  } exception_table[exception_table_length];
 *  // Code属性中包含的属性个数。
 *  u2 attributes_count;
 *  // 能在Code属性的属性表中出现的属性类型有：LineNumberTable、LocalVariableTable、LocalVariableTypeTable、StackMapTable。
 *  attribute_info attributes[attributes_count];
 * }
 * @author zhaooo3
 * @date 4/26/23 2:43 PM
 */
public class CodeAttribute implements AttributeInfo {

    private ConstantPool constantPool;          //  常量池
    private int maxStack;                       //  操作数栈的最大深度
    private int maxLocals;                      //  局部变量表大小
    private byte[] data;                        //  字节码
    private ExceptionTableEntry[] exceptions;   //  异常处理表
    private AttributeInfo[] attributes;         //  属性表

    public CodeAttribute(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        maxStack = reader.readU2();
        maxLocals = reader.readU2();
        int dataLength = (int) reader.readU4();
        data = reader.readBytes(dataLength);
        exceptions = ExceptionTableEntry.readExceptionTable(reader);
        attributes = AttributeInfo.read(reader, constantPool);
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public byte[] getData() {
        return data;
    }

    public ExceptionTableEntry[] getExceptions() {
        return exceptions;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    static class ExceptionTableEntry {

        private int startPC;
        private int endPC;
        private int handlerPC;
        private int catchType;

        ExceptionTableEntry(int startPC, int endPC, int handlerPC, int catchType) {
            this.startPC = startPC;
            this.endPC = endPC;
            this.handlerPC = handlerPC;
            this.catchType = catchType;
        }

        static ExceptionTableEntry[] readExceptionTable(ClassReader reader) {
            int exceptionTableLength = reader.readU2();
            ExceptionTableEntry[] exceptionTable = new ExceptionTableEntry[exceptionTableLength];
            for (int i = 0; i < exceptionTableLength; i++) {
                exceptionTable[i] = new ExceptionTableEntry(reader.readU2(), reader.readU2(), reader.readU2(), reader.readU2());
            }
            return exceptionTable;
        }

        public int getStartPC() {
            return startPC;
        }

        public int getEndPC() {
            return endPC;
        }

        public int getHandlerPC() {
            return handlerPC;
        }

        public int getCatchType() {
            return catchType;
        }

    }
}
