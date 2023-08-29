package cn.zhaooo.jvm.instructions.loads.lload;

import cn.zhaooo.jvm.instructions.base.impl.Index8Instruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 从局部变量表获取long型变量，并推入操作数栈顶
 */
public class LLOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        Long val = localVars.getLong(idx);

        OperandStack operandStack = frame.getOperandStack();
        operandStack.pushLong(val);
    }

}
