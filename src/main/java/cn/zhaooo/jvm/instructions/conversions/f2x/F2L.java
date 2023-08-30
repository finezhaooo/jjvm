package cn.zhaooo.jvm.instructions.conversions.f2x;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * float类型转换为long类型
 */
public class F2L extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        float f = operandStack.popFloat();
        long l = (long) f;
        operandStack.pushLong(l);
    }
}
