package cn.zhaooo.jvm.instructions.loads.dload;

import cn.zhaooo.jvm.instructions.base.impl.Index8Instruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 从局部变量表获取double型变量，并推入操作数栈顶
 */
public class DLOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        double val = localVars.getDouble(idx);

        OperandStack operandStack = frame.getOperandStack();
        operandStack.pushDouble(val);
    }

}