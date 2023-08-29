package cn.zhaooo.jvm.instructions.loads.xaload;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;

/**
 * 加载long类型数组
 */
public class LALOAD extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int idx = stack.popInt();
        Object arrRef = stack.popRef();

        checkNotNull(arrRef);
        long[] longs = arrRef.getLongs();
        checkIndex(longs.length, idx);
        stack.pushLong(longs[idx]);
    }
}