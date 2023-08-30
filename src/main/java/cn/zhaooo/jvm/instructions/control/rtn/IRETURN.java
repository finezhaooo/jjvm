package cn.zhaooo.jvm.instructions.control.rtn;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.Thread;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;

/**
 * 返回int类型的值
 */
public class IRETURN extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Thread thread = frame.getThread();
        Frame currentFrame = thread.popFrame();
        Frame invokerFrame = thread.topFrame();
        int val = currentFrame.getOperandStack().popInt();
        invokerFrame.getOperandStack().pushInt(val);
    }

}
