package cn.zhaooo.jvm.instructions.loads.xaload;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;

/**
 * 加载char类型数组
 */
public class CALOAD extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int idx = stack.popInt();
        Object arrRef = stack.popRef();

        checkNotNull(arrRef);
        char[] chars = arrRef.getChars();
        checkIndex(chars.length, idx);
        stack.pushInt(chars[idx]);
    }
}