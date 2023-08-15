package cn.zhaooo.jvm.rdta.jvmstack;

/**
 * @ClassName: JvmStack
 * @Description: 虚拟机栈
 * @Author: zhaooo
 * @Date: 2023/08/02 21:44
 */
public class JVMStack {
    // 最大栈深
    private int maxSize;
    private int size;
    private Frame _top;

    public JVMStack(int maxSize) {
        this.maxSize = maxSize;
    }

    public void push(Frame frame) {
        if (this.size > this.maxSize) {
            throw new StackOverflowError();
        }
        if (this._top != null) {
            frame.lower = this._top;
        }
        this._top = frame;
        this.size++;
    }

    public Frame pop() {
        if (this._top == null) {
            throw new RuntimeException("jvm stack is empty!");
        }
        Frame frame = this._top;
        // 指向下一个
        this._top = frame.lower;
        // 去掉当前头节点和下一个的连接
        frame.lower = null;
        this.size--;
        return frame;
    }

    public Frame top() {
        if (this._top == null) {
            throw new RuntimeException("jvm stack is empty!");
        }
        return this._top;
    }
}
