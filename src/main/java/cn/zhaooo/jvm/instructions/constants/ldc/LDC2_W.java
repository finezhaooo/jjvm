package cn.zhaooo.jvm.instructions.constants.ldc;

import cn.zhaooo.jvm.instructions.base.impl.Index16Instruction;
import cn.zhaooo.jvm.rdta.heap.constantpool.RunTimeConstantPool;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 用于加载long和double常量
 */
public class LDC2_W extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        RunTimeConstantPool runTimeConstantPool = frame.getMethod().getClazz().getConstantPool();
        java.lang.Object c = runTimeConstantPool.getConstants(idx);

        if (c instanceof Long) {
            stack.pushLong((Long) c);
            return;
        }

        if (c instanceof Double) {
            stack.pushDouble((Double) c);
            return;
        }

        throw new ClassFormatError(c.toString());
    }
}
