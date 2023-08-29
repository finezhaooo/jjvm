package cn.zhaooo.jvm.instructions.loads.iload;

import cn.zhaooo.jvm.instructions.base.impl.Index8Instruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 从局部变量表获取int型变量，并推入操作数栈顶
 */
public class ILOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        int val = localVars.getInt(idx);

        OperandStack operandStack = frame.getOperandStack();
        operandStack.pushInt(val);
    }

}

