package cn.zhaooo.jvm.rdta.jvmstack;

/**
 * @ClassName: Frame
 * @Description: 栈帧
 * @Author: zhaooo
 * @Date: 2023/08/02 21:41
 */
public class Frame {

    // stack is implemented as linked list
    Frame lower;

    // 局部变量表
    private LocalVars localVars;

    // 操作数栈
    private OperandStack operandStack;

    public Frame(int maxLocals, int maxStack) {
        this.localVars = new LocalVars(maxLocals);
        this.operandStack = new OperandStack(maxStack);
    }

    public LocalVars getLocalVars(){
        return localVars;
    }

    public OperandStack getOperandStack(){
        return operandStack;
    }
}
