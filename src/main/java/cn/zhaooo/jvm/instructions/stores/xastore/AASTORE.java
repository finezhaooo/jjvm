package cn.zhaooo.jvm.instructions.stores.xastore;

import cn.zhaooo.jvm.instructions.base.impl.NoOperandsInstruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;

public class AASTORE extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        //  要赋给数组元素的值
        Object ref = stack.popRef();
        //  数组索引
        int idx = stack.popInt();
        //  数组引用
        Object arrRef = stack.popRef();

        checkNotNull(arrRef);
        Object[] refs = arrRef.getRefs();
        checkIndex(refs.length, idx);

        //  按索引给数组元素赋值
        refs[idx] = ref;
    }
}
