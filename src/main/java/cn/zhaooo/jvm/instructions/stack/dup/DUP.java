package cn.zhaooo.jvm.instructions.stack.dup;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
import cn.zhaooo.jvm.rdta.jvmstack.Slot;

/**
 * 复制栈顶的单个变量
 * bottom -> top
 * [...][c][b][a]
 *              \_
 *                |
 *                V
 * [...][c][b][a][a]
 */
public class DUP extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        Slot slot = operandStack.popSlot();
        // 如果不new一个copy，两个slot指向同一个对象会导致炒作一个slot时另一个slot也改变，出现bug
        Slot copy = new Slot(slot.val, slot.ref);
        operandStack.pushSlot(slot);
        operandStack.pushSlot(copy);
    }
}
