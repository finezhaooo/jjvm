package cn.zhaooo.jvm.instructions.math.mul;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * double类型乘法
 */
public class DMUL extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        double v2 = operandStack.popDouble();
        double v1 = operandStack.popDouble();
        double res = v1 * v2;
        operandStack.pushDouble(res);
    }
}
