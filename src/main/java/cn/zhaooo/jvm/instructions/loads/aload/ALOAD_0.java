package cn.zhaooo.jvm.instructions.loads.aload;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;

public class ALOAD_0 extends NoOperandsInstruction {
    
    @Override
    public void execute(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        Object ref = localVars.getRef(0);

        OperandStack operandStack = frame.getOperandStack();
        operandStack.pushRef(ref);
    }

}