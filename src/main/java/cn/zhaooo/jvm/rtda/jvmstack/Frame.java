package cn.zhaooo.jvm.rtda.jvmstack;

import cn.zhaooo.jvm.rtda.Thread;
import cn.zhaooo.jvm.rtda.heap.methodarea.Method;

/**
 * @description: 栈帧
 * @author zhaooo3
 * @date 5/9/23 3:34 PM
 */
public class Frame {
    Frame lower;
    //  局部变量表
    private LocalVars localVars;
    //  操作数栈
    private OperandStack operandStack;
    private int nextPC;

    public Frame(Thread thread, Method method) {
        localVars = new LocalVars(method.maxLocals);
        operandStack = new OperandStack(method.maxStack);
    }

    public LocalVars getLocalVars() {
        return localVars;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }

    public void setNextPC(int nextPC) {
        this.nextPC = nextPC;
    }

    public int getNextPC() {
        return nextPC;
    }
}
