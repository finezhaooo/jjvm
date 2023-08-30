package cn.zhaooo.jvm.instructions.conversions.d2x;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * double类型转换为int类型
 */
public class D2I extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        double d = operandStack.popDouble();
        int i = (int) d;
        operandStack.pushInt(i);
    }
}
