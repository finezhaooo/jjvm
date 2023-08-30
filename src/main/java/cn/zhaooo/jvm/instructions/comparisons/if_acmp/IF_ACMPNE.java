package cn.zhaooo.jvm.instructions.comparisons.if_acmp;

import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.instructions.base.impl.BranchInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 	if references are not equal, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 | branchbyte2)
 */
public class IF_ACMPNE extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        Object ref2 = operandStack.popRef();
        Object ref1 = operandStack.popRef();

        if (!ref1.equals(ref2)) {
            Instruction.branch(frame, offset);
        }
    }
}