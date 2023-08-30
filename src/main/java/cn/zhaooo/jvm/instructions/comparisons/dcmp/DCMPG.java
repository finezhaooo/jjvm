package cn.zhaooo.jvm.instructions.comparisons.dcmp;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 比较double变量
 * compare two doubles, 1 on NaN
 */
public class DCMPG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        double v2 = operandStack.popDouble();
        double v1 = operandStack.popDouble();

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

        //  当两个double变量中至少有一个是NaN时，比较结果是1
        operandStack.pushInt(1);
    }

}
