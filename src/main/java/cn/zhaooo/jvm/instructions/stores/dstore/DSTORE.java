package cn.zhaooo.jvm.instructions.stores.dstore;

import cn.zhaooo.jvm.instructions.base.impl.Index8Instruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 将double型变量从操作数栈顶弹出，并存入局部变量表
 */
public class DSTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        double val = operandStack.popDouble();

        LocalVars localVars = frame.getLocalVars();
        localVars.setDouble(idx, val);
    }

}
