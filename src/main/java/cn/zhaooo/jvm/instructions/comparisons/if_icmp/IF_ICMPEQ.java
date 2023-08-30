package cn.zhaooo.jvm.instructions.comparisons.if_icmp;

import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.instructions.base.impl.BranchInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 	if ints are equal, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 | branchbyte2)
 */
public class IF_ICMPEQ extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        int val2 = operandStack.popInt();
        int val1 = operandStack.popInt();
        if (val1 == val2) {
            Instruction.branch(frame, this.offset);
        }
    }
    
}
