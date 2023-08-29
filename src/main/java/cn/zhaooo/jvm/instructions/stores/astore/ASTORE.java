package cn.zhaooo.jvm.instructions.stores.astore;

import cn.zhaooo.jvm.instructions.base.impl.Index8Instruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;

/**
 * 将引用类型变量从操作数栈顶弹出，并存入局部变量表
 */
public class ASTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        Object ref = operandStack.popRef();

        LocalVars localVars = frame.getLocalVars();
        localVars.setRef(idx, ref);
    }

}
