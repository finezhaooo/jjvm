package cn.zhaooo.jvm.instructions.loads.aload;

import cn.zhaooo.jvm.instructions.base.impl.Index8Instruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;

/**
 * 从局部变量表获取引用型标量，并推入操作数栈顶
 */
public class ALOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        Object ref = localVars.getRef(idx);

        OperandStack operandStack = frame.getOperandStack();
        operandStack.pushRef(ref);
    }

}
