package cn.zhaooo.jvm.instructions.comparisons.lcmp;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 比较long变量
 * push 0 if the two longs are the same, 1 if value1 is greater than value2, -1 otherwise
 */
public class LCMP extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        long v2 = operandStack.popLong();
        long v1 = operandStack.popLong();

        if (v1 > v2) {
            operandStack.pushInt(1);
            return;
        }

        if (v1 == v2) {
            operandStack.pushInt(0);
            return;
        }

        operandStack.pushInt(-1);
    }
}
