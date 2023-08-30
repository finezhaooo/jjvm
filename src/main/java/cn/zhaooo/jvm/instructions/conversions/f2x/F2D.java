package cn.zhaooo.jvm.instructions.conversions.f2x;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * float类型转换为double类型
 */
public class F2D extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        float f = operandStack.popFloat();
        double d = f;
        operandStack.pushDouble(d);
    }
}
