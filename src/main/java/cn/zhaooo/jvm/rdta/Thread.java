package cn.zhaooo.jvm.rdta;

import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.JvmStack;

/**
 * @ClassName: Thread
 * @Description: 线程
 * @Author: zhaooo
 * @Date: 2023/08/02 21:47
 */
public class Thread {

    //Program Counter
    private int pc;

    //虚拟机栈
    private JvmStack stack;

    public Thread() {
        this.stack = new JvmStack(1024);
    }

    public int getPc() {
        return this.pc;
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

    public Frame currentFrame() {
        return this.stack.top();
    }
}
