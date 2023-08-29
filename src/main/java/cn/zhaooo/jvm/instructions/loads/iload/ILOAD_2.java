package cn.zhaooo.jvm.instructions.loads.iload;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

public class ILOAD_2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        int val = localVars.getInt(2);

        OperandStack operandStack = frame.getOperandStack();
        operandStack.pushInt(val);
    }

}
