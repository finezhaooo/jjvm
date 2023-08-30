package cn.zhaooo.jvm.instructions.math.neg;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * int类型取反
 */
public class INEG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        int val = operandStack.popInt();
        operandStack.pushDouble(-val);
    }

}

