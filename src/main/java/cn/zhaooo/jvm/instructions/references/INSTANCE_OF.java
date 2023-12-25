package cn.zhaooo.jvm.instructions.references;

import cn.zhaooo.jvm.instructions.base.impl.Index16Instruction;
import cn.zhaooo.jvm.rdta.heap.constantpool.ClassRef;
import cn.zhaooo.jvm.rdta.heap.constantpool.RunTimeConstantPool;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
/**
 * 判断对象是否是某个类的实例，或者对象的类是否实现了某个接口，
 * instanceof指令需要两个操作数，第一个操作数是2字节索引，从方法的字节码中获取，
 * 第二个操作数是对象引用，从操作数栈中弹出
 */
public class INSTANCE_OF extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        //  先弹出对象引用，如果是null，则把0推入操作数栈
        OperandStack stack = frame.getOperandStack();
        Object ref = stack.popRef();
        if (null == ref){
            stack.pushInt(0);
            return;
        }

        //  如果对象引用不是null，则解析类符号引用，判断对象是否是类的实例，并将结果推入操作数栈
        RunTimeConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) cp.getConstants(idx);
        Class clazz = classRef.getResolvedClass();
        if (ref.isInstanceOf(clazz)){
            stack.pushInt(1);
        } else {
            stack.pushInt(0);
        }
    }
}
