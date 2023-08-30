package cn.zhaooo.jvm.instructions.math.sh;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * int类型左移
 */
public class ISHL extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        //  v2指出要移位多少比特
        int v2 = operandStack.popInt();
        //  v1要进行位移操作的变量
        int v1 = operandStack.popInt();
        // v2 & 0x1f相当于对v2进行模32的运算
        int s = v2 & 0x1f;
        int res = v1 << s;
        operandStack.pushInt(res);
    }

}
