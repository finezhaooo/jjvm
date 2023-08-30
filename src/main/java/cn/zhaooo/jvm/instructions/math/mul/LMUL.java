package cn.zhaooo.jvm.instructions.math.mul;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * long类型乘法
 */
public class LMUL extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        long v2 = operandStack.popLong();
        long v1 = operandStack.popLong();
        long res = v1 * v2;
        operandStack.pushLong(res);
    }

}