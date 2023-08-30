package cn.zhaooo.jvm.instructions.math.sh;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * long类型左移
 */
public class LSHL extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        int v2 = operandStack.popInt();
        long v1 = operandStack.popLong();
        // v2 & 0x3f相当于对v2进行模63的运算，
        int s = v2 & 0x3f;
        long res = v1 << s;
        operandStack.pushLong(res);
    }

}

