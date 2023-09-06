package cn.zhaooo.jvm.instructions.extended.ifnull;

import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.instructions.base.impl.BranchInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 引用是null，进行跳转
 */
public class IFNULL extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        Object ref = operandStack.popRef();

        if (null == ref) {
            Instruction.branch(frame, offset);
        }
    }

}
