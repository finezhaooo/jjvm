package cn.zhaooo.jvm.instructions.loads.lload;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

public class LLOAD_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        Long val = localVars.getLong(1);

        OperandStack operandStack = frame.getOperandStack();
        operandStack.pushLong(val);
    }

}
