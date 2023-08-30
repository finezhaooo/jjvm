package cn.zhaooo.jvm.instructions.conversions.d2x;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * double类型转换为float类型
 */
public class D2F extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        double d = operandStack.popDouble();
        float f = (float) d;
        operandStack.pushFloat(f);
    }
}
