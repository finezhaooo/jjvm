package cn.zhaooo.jvm.instructions.stack.pop;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * double和long变量在操作数栈中占据两个位置，需要使用pop2指令弹出
 * bottom -> top
 * [...][c][b][a]
 *          |  |
 *          V  V
 * [...][c]
 */
public class POP2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        operandStack.popSlot();
        operandStack.popSlot();
    }

}
