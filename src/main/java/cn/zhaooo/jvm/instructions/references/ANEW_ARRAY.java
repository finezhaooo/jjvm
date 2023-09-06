package cn.zhaooo.jvm.instructions.references;

import cn.zhaooo.jvm.instructions.base.impl.Index16Instruction;
import cn.zhaooo.jvm.rdta.heap.constantpool.ClassRef;
import cn.zhaooo.jvm.rdta.heap.constantpool.RunTimeConstantPool;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 创建引用类型数组，anewarray指令也需要两个操作数，
 * 第一个操作数是2字节索引，来自字节码，通过这个索引可以从当前类的运行时常量池中找到一个类符号引用，
 * 第二个操作数是数组长度，从操作数栈中弹出
 */
public class ANEW_ARRAY extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        RunTimeConstantPool runTimeConstantPool = frame.getMethod().getClazz().getConstantPool();
        // 组件类型
        ClassRef classRef = (ClassRef) runTimeConstantPool.getConstants(idx);
        Class componentClass = classRef.resolvedClass();

        OperandStack stack = frame.getOperandStack();
        int count = stack.popInt();
        if (count < 0) {
            throw new NegativeArraySizeException();
        }

        Class arrClass = componentClass.getArrayClass();
        Object arr = arrClass.newArray(count);
        stack.pushRef(arr);
    }
}
