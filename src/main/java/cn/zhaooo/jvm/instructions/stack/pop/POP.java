package cn.zhaooo.jvm.instructions.stack.pop;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 将栈顶变量弹出，只能用于弹出int、float等占一个操作数栈位置的变量
 * bottom -> top
 * [...][c][b][a]
 *             |
 *             V
 * [...][c][b]
 */
public class POP extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        operandStack.popSlot();
    }

}
