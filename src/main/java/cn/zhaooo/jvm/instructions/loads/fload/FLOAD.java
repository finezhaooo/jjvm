package cn.zhaooo.jvm.instructions.loads.fload;

import cn.zhaooo.jvm.instructions.base.impl.Index8Instruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 从局部变量表获取float型变量，并推入操作数栈顶
 */
public class FLOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        Float val = localVars.getFloat(idx);

        OperandStack operandStack = frame.getOperandStack();
        operandStack.pushFloat(val);
    }

}
