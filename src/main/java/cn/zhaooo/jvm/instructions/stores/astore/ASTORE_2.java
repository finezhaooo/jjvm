package cn.zhaooo.jvm.instructions.stores.astore;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;

public class ASTORE_2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        Object ref = operandStack.popRef();

        LocalVars localVars = frame.getLocalVars();
        localVars.setRef(2, ref);
    }

}

