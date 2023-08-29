package cn.zhaooo.jvm.instructions.loads.xaload;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;

/**
 * 加载引用类型数组
 */
public class AALOAD extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        //  弹出第一个操作数，数组索引
        int idx = stack.popInt();
        //  弹出第二个操作数，数组引用
        Object arrRef = stack.popRef();

        checkNotNull(arrRef);
        Object[] refs = arrRef.getRefs();
        checkIndex(refs.length, idx);

        //  一切正常，则按索引取出数组元素，推入操作数栈顶
        stack.pushRef(refs[idx]);
    }
}
