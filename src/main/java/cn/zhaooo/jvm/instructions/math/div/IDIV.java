package cn.zhaooo.jvm.instructions.math.div;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * int类型除法
 */
public class IDIV extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        int v2 = operandStack.popInt();
        int v1 = operandStack.popInt();
        if (v2 == 0){
            throw new ArithmeticException("/ by zero");
        }
        int res = v1 / v2;
        operandStack.pushInt(res);
    }
}
