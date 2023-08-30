package cn.zhaooo.jvm.instructions.comparisons.ifcond;

import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.instructions.base.impl.BranchInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

public class IFGT extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        int val = operandStack.popInt();

        if (val > 0) {
            Instruction.branch(frame, offset);
        }
    }

}
