package cn.zhaooo.jvm.rtda.jvmstack;

import cn.zhaooo.jvm.rtda.heap.methodarea.Object;


/**
 * @description: 操作数栈
 * @author zhaooo3
 * @date 5/9/23 3:35 PM
 */
public class OperandStack {
    private int size = 0;
    private Slot[] slots;

    // CodeAttribute的maxStack，编译期确定
    public OperandStack(int maxStack) {
        if (maxStack > 0) {
            slots = new Slot[maxStack];
            for (int i = 0; i < maxStack; i++) {
                slots[i] = new Slot();
            }
        }
    }

    public void pushInt(int val) {
        slots[size].num = val;
        size++;
    }

    public int popInt() {
        size--;
        return slots[size].num;
    }

    public void pushFloat(float val) {
        slots[size].num = (int) val;
        size++;
    }

    public float popFloat() {
        size--;
        return slots[size].num;
    }

    //long占2个slot
    public void pushLong(long val) {
        // 强制类型转化的原理是砍了前面的字节
        slots[size].num = (int) val;
        slots[size + 1].num = (int) (val >> 32);
        size += 2;
    }

    // 取出2个slot
    public long popLong() {
        size -= 2;
        int low = slots[size].num;
        int high = slots[size + 1].num;
        return (long) (high) << 32 | (long) (low);
    }

    public void pushDouble(double val) {
        this.pushLong((long) val);
    }

    public double popDouble() {
        return this.popLong();
    }

    public void pushRef(Object ref) {
        slots[size].ref = ref;
        size++;
    }

    public Object popRef() {
        size--;
        Object ref = slots[size].ref;
        slots[size].ref = null;
        return ref;
    }

    public void pushSlot(Slot slot) {
        slots[size] = slot;
        size++;
    }

    public Slot popSlot() {
        size--;
        return slots[size];
    }

    public Slot[] getSlots() {
        return slots;
    }
}
