package cn.zhaooo.jvm.instructions.stores.istore;

import cn.zhaooo.jvm.instructions.base.impl.Index8Instruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 将int型变量从操作数栈顶弹出，并存入局部变量表
 */
public class ISTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        int val = operandStack.popInt();

        LocalVars localVars = frame.getLocalVars();
        localVars.setInt(idx, val);
    }

}