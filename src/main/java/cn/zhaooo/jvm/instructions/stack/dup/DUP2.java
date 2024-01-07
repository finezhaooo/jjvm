package cn.zhaooo.jvm.instructions.stack.dup;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
import cn.zhaooo.jvm.rdta.jvmstack.Slot;

/**
 * 复制栈顶的两个变量
 * bottom -> top
 * [...][c][b][a]____
 *           \____   |
 *                |  |
 *                V  V
 * [...][c][b][a][b][a]
 */
public class DUP2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        Slot slot1 = operandStack.popSlot();
        Slot slot2 = operandStack.popSlot();
        Slot copy1 = new Slot(slot1.val, slot1.ref);
        Slot copy2 = new Slot(slot2.val, slot2.ref);
        operandStack.pushSlot(slot2);
        operandStack.pushSlot(slot1);
        operandStack.pushSlot(copy2);
        operandStack.pushSlot(copy1);
    }
}
