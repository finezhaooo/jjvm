package cn.zhaooo.jvm.instructions.conversions.l2x;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * long类型转换为double类型
 */
public class L2D extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        long l = operandStack.popLong();
        float f = l;
        operandStack.pushFloat(f);
    }

}
