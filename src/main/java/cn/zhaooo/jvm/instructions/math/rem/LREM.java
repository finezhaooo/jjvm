package cn.zhaooo.jvm.instructions.math.rem;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * long类型求余
 */
public class LREM extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        long v2 = operandStack.popLong();
        long v1 = operandStack.popLong();
        if (v2 == 0){
            throw new ArithmeticException("/ by zero");
        }
        long res = v1 % v2;
        operandStack.pushLong(res);
    }

}
