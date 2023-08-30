package cn.zhaooo.jvm.instructions.control.rtn;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.Thread;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;

/**
 * 没有返回值
 */
public class RETURN extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Thread thread = frame.getThread();
        // 推出当前方法
        thread.popFrame();
    }
}
