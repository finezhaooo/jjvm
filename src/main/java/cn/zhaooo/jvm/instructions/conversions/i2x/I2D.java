package cn.zhaooo.jvm.instructions.conversions.i2x;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * int类型转换为double类型
 */
public class I2D extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        int i = operandStack.popInt();
        double d = i;
        operandStack.pushDouble(d);
    }
}