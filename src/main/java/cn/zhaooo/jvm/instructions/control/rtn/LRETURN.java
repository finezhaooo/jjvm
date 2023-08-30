package cn.zhaooo.jvm.instructions.control.rtn;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.Thread;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;

/**
 * 返回long类型的值
 */
public class LRETURN extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Thread thread = frame.getThread();
        Frame currentFrame = thread.popFrame();
        Frame invokerFrame = thread.topFrame();
        long val = currentFrame.getOperandStack().popLong();
        invokerFrame.getOperandStack().pushLong(val);
    }
}
