package cn.zhaooo.jvm.instructions.math.neg;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * double类型取反
 */
public class DNEG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        double val = operandStack.popDouble();
        operandStack.pushDouble(-val);
    }

}