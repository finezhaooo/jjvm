package cn.zhaooo.jvm.instructions.constants.nop;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;

/**
 * nop指令什么也不做
 */
public class NOP extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        //  nothing to do
    }
}
