package cn.zhaooo.jvm.rdta.jvmstack;

import cn.zhaooo.jvm.rdta.heap.methodarea.Method;
import cn.zhaooo.jvm.rdta.Thread;

/**
 * @ClassName: Frame
 * @Description: 栈帧
 * @Author: zhaooo
 * @Date: 2023/08/02 21:41
 */
public class Frame {

    Frame lower;
    private LocalVars localVars;        //  局部变量表
    private OperandStack operandStack;  //  操作数栈
    private Thread thread;              //  当前线程
    private Method method;              //  当前方法（用于支持动态链接）
    // returnAddress                    //  返回地址
    private int nextPC;                 //  下一条指令地址

    // 一层栈帧对应一个方法
    public Frame(Thread thread, Method method) {
        this.thread = thread;
        this.method = method;
        localVars = new LocalVars(method.maxLocals);
        operandStack = new OperandStack(method.maxStack);
    }

    public LocalVars getLocalVars() {
        return localVars;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }

    public Thread getThread() {
        return thread;
    }

    public Method getMethod() {
        return method;
    }

    public int getNextPC() {
        return nextPC;
    }

    public void setNextPC(int nextPC) {
        this.nextPC = nextPC;
    }

    /**
     * 将当前栈帧的nextPC设置为线程的PC
     */
    public void revertNextPC() {
        nextPC = thread.getPC();
    }
}
