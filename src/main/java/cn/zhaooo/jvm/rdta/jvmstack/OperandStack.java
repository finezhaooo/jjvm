package cn.zhaooo.jvm.rdta.jvmstack;

import cn.zhaooo.jvm.rdta.heap.methodarea.Object;

/**
 * @ClassName: OperandStack
 * @Description: 操作数栈
 * @Author: zhaooo
 * @Date: 2023/08/02 21:46
 */
public class OperandStack {
    // 栈顶指针（指向空）
    private int top = 0;
    // arr实现栈
    private Slot[] slots;

    public OperandStack(int maxStack) {
        if (maxStack >= 0) {
            slots = new Slot[maxStack];
            for (int i = 0; i < maxStack; i++) {
                slots[i] = new Slot();
            }
        }
    }

    // boolean、byte、char、short、int
    public void pushInt(int val) {
        slots[top++].val = val;
    }

    public int popInt() {
        return slots[--top].val;
    }

    public void pushFloat(float val) {
        slots[top++].val = (int) val;
    }

    public float popFloat() {
        // 小转大自动转换
        return slots[--top].val;
    }

    // long占用两个slot
    public void pushLong(long val) {
        // 小端存储的,即低位字节在前,高位字节在后
        // low，大转小保留低位
        slots[top++].val = (int) val;
        // high
        slots[top++].val = (int) (val >> 32);
    }

    public long popLong() {
        // 高位先出栈
        int high = slots[--top].val;
        int low = slots[--top].val;
        // 这里low & 0xFFFFFFFFL是为了将low转变为无符号long，否则当low是负数时和long进行或运算时会将low用符号位补齐为long
        return (long) high << 32 | (low & 0xFFFFFFFFL);
    }

    // double占用两个slot
    public void pushDouble(double val) {
        this.pushLong((long) val);
    }

    public double popDouble() {
        return this.popLong();
    }

    public void pushRef(Object ref) {
        slots[top++].ref = ref;
    }

    public Object popRef() {
        Object ref = slots[--top].ref;
        slots[top].ref = null;
        return ref;
    }

    public void pushSlot(Slot slot) {
        slots[top++] = slot;
    }

    public Slot popSlot() {
        return slots[--top];
    }

    public int getTop() {
        return top;
    }

    public Slot[] getSlots() {
        return slots;
    }

    /**
     * 返回距离操作数栈顶n个单元格的引用变量
     */
    public Object getRefFromTop(int n) {
        return slots[top - 1 - n].ref;
    }

}
