package cn.zhaooo.jvm.rdta;

import cn.zhaooo.jvm.rdta.heap.methodarea.Method;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.JVMStack;

/**
 * @ClassName: Thread
 * @Description: 线程
 * @Author: zhaooo
 * @Date: 2023/08/02 21:47
 */
public class Thread {

    private int pc;         //  PC寄存器，线程私有
    private final JVMStack stack; //  Java虚拟机栈，线程私有

    public Thread() {
        this.stack = new JVMStack(1024);
    }

    public int getPC() {
        return pc;
    }

    public void setPC(int pc) {
        this.pc = pc;
    }

    public void pushFrame(Frame frame) {
        this.stack.push(frame);
    }

    public Frame popFrame() {
        return this.stack.pop();
    }

    public Frame topFrame() {
        return this.stack.top();
    }

    public Frame currentFrame() {
        return this.stack.top();
    }

    public Frame newFrame(Method method) {
        return new Frame(this, method);
    }

    public boolean isStackEmpty() {
        return stack.isEmpty();
    }
    public JVMStack getStack() {
        return stack;
    }
}
