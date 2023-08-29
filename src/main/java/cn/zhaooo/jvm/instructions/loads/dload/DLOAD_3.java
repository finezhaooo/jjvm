package cn.zhaooo.jvm.instructions.loads.dload;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

public class DLOAD_3 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        double val = localVars.getDouble(3);

        OperandStack operandStack = frame.getOperandStack();
        operandStack.pushDouble(val);
    }

}

