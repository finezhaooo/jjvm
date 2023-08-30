package cn.zhaooo.jvm.instructions.control;

import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.instructions.base.impl.BranchInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;

/**
 * 无条件跳转
 */
public class GOTO extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        Instruction.branch(frame, offset);
    }
}
