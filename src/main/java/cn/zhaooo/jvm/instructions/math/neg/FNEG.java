package cn.zhaooo.jvm.instructions.math.neg;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * float类型取反
 */
public class FNEG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        float val = operandStack.popFloat();
        operandStack.pushDouble(-val);
    }

}
