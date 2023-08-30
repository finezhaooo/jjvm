package cn.zhaooo.jvm.instructions.comparisons.fcmp;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 比较float变量
 */
public class FCMPL extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        double v2 = operandStack.popFloat();
        double v1 = operandStack.popFloat();

        if (v1 > v2) {
            operandStack.pushInt(1);
            return;
        }

        if (v1 == v2) {
            operandStack.pushInt(0);
            return;
        }

        if (v1 < v2) {
            operandStack.pushInt(-1);
            return;
        }

        //  当两个float变量中至少有一个是NaN时，比较结果是-1
        operandStack.pushInt(-1);
    }

}
