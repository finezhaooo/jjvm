package cn.zhaooo.jvm.instructions.loads.fload;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

public class FLOAD_2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        Float val = localVars.getFloat(2);

        OperandStack operandStack = frame.getOperandStack();
        operandStack.pushFloat(val);
    }

}
