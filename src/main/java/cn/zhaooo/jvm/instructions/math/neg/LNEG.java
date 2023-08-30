package cn.zhaooo.jvm.instructions.math.neg;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * long类型取反
 */
public class LNEG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        long val = operandStack.popLong();
        operandStack.pushLong(-val);
    }

}
