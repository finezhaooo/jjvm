package cn.zhaooo.jvm.instructions.constants.ldc;

import cn.zhaooo.jvm.instructions.base.impl.Index16Instruction;
import cn.zhaooo.jvm.rdta.heap.constantpool.RunTimeConstantPool;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;
import cn.zhaooo.jvm.rdta.heap.methodarea.StringPool;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;


/**
 * 用于加载int、float和字符串常量，java.lang.Class实例或者MethodType和MethodHandle实例
 */
public class LDC_W extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        Class clazz = frame.getMethod().getClazz();
        RunTimeConstantPool runTimeConstantPool = frame.getMethod().getClazz().getRunTimeConstantPool();
        java.lang.Object c = runTimeConstantPool.getConstants(idx);

        if (c instanceof Integer) {
            stack.pushInt((Integer) c);
            return;
        }

        if (c instanceof Float) {
            stack.pushFloat((Float) c);
            return;
        }

        if (c instanceof String) {
            Object internedStr = StringPool.jString(clazz.getLoader(), (String) c);
            stack.pushRef(internedStr);
        }
        throw new ClassFormatError(c.toString());
    }
}
