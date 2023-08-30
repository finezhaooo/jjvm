package cn.zhaooo.jvm.instructions.comparisons.if_icmp;

import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.instructions.base.impl.BranchInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

public class IF_ICMPLT extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        int val2 = operandStack.popInt();
        int val1 = operandStack.popInt();
        if (val1 < val2) {
            Instruction.branch(frame, offset);
        }
    }

}