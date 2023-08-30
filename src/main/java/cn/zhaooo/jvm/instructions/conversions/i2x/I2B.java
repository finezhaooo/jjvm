package cn.zhaooo.jvm.instructions.conversions.i2x;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * int类型转换为byte类型
 */
public class I2B extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        int i = operandStack.popInt();
        int b = (byte) i;
        operandStack.pushInt(b);
    }

}
