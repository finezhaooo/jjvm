package cn.zhaooo.jvm.rtda.jvmstack;

/**
 * @description: Java虚拟机栈
 * @author zhaooo3
 * @date 5/9/23 3:32 PM
 */
public class JVMStack {

    private int maxSize;
    //  栈的当前大小
    private int size;
    //  栈顶
    private Frame _top;
    // hotSpot没有规定最大栈深度
    public JVMStack(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * 栈帧入栈
     */
    public void push(Frame frame) {
        if (size > maxSize) {
            //  如果栈已经满了，按照Java虚拟机规范，抛出StackOverflowError异常
            throw new StackOverflowError();
        }

        if (_top != null) {
            frame.lower = _top;
        }

        _top = frame;
        size++;
    }

    /**
     * 栈帧出栈
     */
    public Frame pop() {
        if (_top == null) {
            throw new RuntimeException("jvm stack is empty!");
        }
        Frame top = _top;
        // top.lower中也保存有lower
        _top = top.lower;
        top.lower = null;
        size--;
        return top;
    }

    /**
     * 返回栈顶栈帧
     */
    public Frame top() {
        if (_top == null) {
            throw new RuntimeException("jvm stack is empty!");
        }
        return _top;
    }

}