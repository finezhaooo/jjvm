package cn.zhaooo.jvm.instructions.references;

import cn.zhaooo.jvm.ClassLoader;
import cn.zhaooo.jvm.instructions.base.BytecodeReader;
import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 创建基本类型数组，包括boolean[]、byte[]、char[]、short[]、int[]、long[]、float[]和double[]，
 * newarray指令需要两个操作数，第一个操作数是一个1字节整数，在字节码中紧跟在指令操作码后面，
 * 第二个操作数是count，从操作数栈中弹出，表示数组长度
 */
public class NEW_ARRAY implements Instruction {

    private byte atype; //  创建数组的类型

    @Override
    public void fetchOperands(BytecodeReader reader) {
        atype = reader.readByte();
    }

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();

        //  如果count小于0，则抛出NegativeArraySizeException异常
        int count = operandStack.popInt();
        if (count < 0) {
            throw new NegativeArraySizeException();
        }

        //  根据atype值使用当前类的类加载器加载数组类
        ClassLoader classLoader = frame.getMethod().getClazz().getLoader();
        Class arrClass = getPrimitiveArrayClass(classLoader, atype);

        //  创建数组对象并推入操作数栈
        Object arr = arrClass.newArray(count);
        operandStack.pushRef(arr);
    }

    private Class getPrimitiveArrayClass(ClassLoader loader, byte atype) {
        switch (atype) {
            case ArrayType.AT_BOOLEAN:
                return loader.loadClass("[Z");
            case ArrayType.AT_BYTE:
                return loader.loadClass("[B");
            case ArrayType.AT_CHAR:
                return loader.loadClass("[C");
            case ArrayType.AT_SHORT:
                return loader.loadClass("[S");
            case ArrayType.AT_INT:
                return loader.loadClass("[I");
            case ArrayType.AT_LONG:
                return loader.loadClass("[J");
            case ArrayType.AT_FLOAT:
                return loader.loadClass("[F");
            case ArrayType.AT_DOUBLE:
                return loader.loadClass("[D");
            default:
                throw new RuntimeException("Invalid atype!");
        }
    }

    static class ArrayType {
        static final byte AT_BOOLEAN = 4;
        static final byte AT_CHAR = 5;
        static final byte AT_FLOAT = 6;
        static final byte AT_DOUBLE = 7;
        static final byte AT_BYTE = 8;
        static final byte AT_SHORT = 9;
        static final byte AT_INT = 10;
        static final byte AT_LONG = 11;
    }
}
