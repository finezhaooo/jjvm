package cn.zhaooo.jvm.instructions.math.add;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * float类型加法
 */
public class FADD extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        float v2 = operandStack.popFloat();
        float v1 = operandStack.popFloat();
        float res = v1 + v2;
        operandStack.pushFloat(res);
    }

}
