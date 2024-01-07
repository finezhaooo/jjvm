package cn.zhaooo.jvm.instructions.stack.dup;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
import cn.zhaooo.jvm.rdta.jvmstack.Slot;

/**
 * bottom -> top
 * [...][d][c][b][a]
 *        ____/ __/
 *       |   __/
 *       V  V
 * [...][b][a][d][c][b][a]
 */
public class DUP2_X2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        Slot slot1 = operandStack.popSlot();
        Slot slot2 = operandStack.popSlot();
        Slot slot3 = operandStack.popSlot();
        Slot slot4 = operandStack.popSlot();
        Slot copy1 = new Slot(slot1.val, slot1.ref);
        Slot copy2 = new Slot(slot2.val, slot2.ref);
        operandStack.pushSlot(copy2);
        operandStack.pushSlot(copy1);
        operandStack.pushSlot(slot4);
        operandStack.pushSlot(slot3);
        operandStack.pushSlot(slot2);
        operandStack.pushSlot(slot1);
    }
}
