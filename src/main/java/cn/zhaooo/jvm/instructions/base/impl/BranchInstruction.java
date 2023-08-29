package cn.zhaooo.jvm.instructions.base.impl;

import cn.zhaooo.jvm.instructions.base.BytecodeReader;
import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;

/**
 * 跳转指令
 */
public class BranchInstruction implements Instruction {

    protected int offset;   //  跳转偏移量

    @Override
    public void fetchOperands(BytecodeReader reader) {
        offset = reader.readShort();
    }

    @Override
    public void execute(Frame frame) {

    }
}
