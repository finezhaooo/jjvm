package cn.zhaooo.jvm.instructions.math.xor;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * long类型按位异或
 */
public class LXOR extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        long v1 = operandStack.popLong();
        long v2 = operandStack.popLong();
        long res = v1 ^ v2;
        operandStack.pushLong(res);
    }

}

