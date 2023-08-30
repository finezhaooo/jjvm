package cn.zhaooo.jvm.instructions.comparisons.ifcond;

import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.instructions.base.impl.BranchInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 	if value is 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 | branchbyte2)
 */
public class IFEQ extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        int val = operandStack.popInt();

        if (0 == val) {
            Instruction.branch(frame, offset);
        }
    }

}
