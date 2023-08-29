package cn.zhaooo.jvm.instructions.loads.xaload;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;

/**
 * 加载float类型数组
 */
public class FALOAD extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int idx = stack.popInt();
        Object arrRef = stack.popRef();

        checkNotNull(arrRef);
        float[] floats = arrRef.getFloats();
        checkIndex(floats.length, idx);
        stack.pushFloat(floats[idx]);
    }
}