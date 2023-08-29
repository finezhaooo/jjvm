package cn.zhaooo.jvm.instructions.stores.fstore;

import cn.zhaooo.jvm.instructions.base.impl.Index8Instruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 将float型变量从操作数栈顶弹出，并存入局部变量表
 */
public class FSTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        float val = operandStack.popFloat();

        LocalVars localVars = frame.getLocalVars();
        localVars.setFloat(idx, val);
    }

}
