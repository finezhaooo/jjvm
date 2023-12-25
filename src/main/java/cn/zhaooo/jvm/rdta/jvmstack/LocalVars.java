package cn.zhaooo.jvm.rdta.jvmstack;

import cn.zhaooo.jvm.rdta.heap.methodarea.Object;

/**
 * @ClassName: LocalVars
 * @Description: 局部变量表
 * @Author: zhaooo
 * @Date: 2023/08/02 21:45
 */
public class LocalVars {
    private Slot[] slots;

    // maxLocals在编译时确定
    public LocalVars(int maxLocals) {
        if (maxLocals >= 0) {
            slots = new Slot[maxLocals];
            for (int i = 0; i < maxLocals; i++) {
                slots[i] = new Slot();
            }
        }
    }

    // boolean、byte、char、short、int
    public void setInt(int idx, int val) {
        this.slots[idx].val = val;
    }

    public int getInt(int idx) {
        return slots[idx].val;
    }

    public void setFloat(int idx, float val) {
        this.slots[idx].val = (int) val;
    }

    public float getFloat(int idx) {
        return this.slots[idx].val;
    }

    // long占用两个slot
    public void setLong(int idx, long val) {
        // 小端存储 低位在前 大转小保留低位
        this.slots[idx].val = (int) val;
        this.slots[idx + 1].val = (int) (val >> 32);
    }

    public long getLong(int idx) {
        int low = this.slots[idx].val;
        int high = this.slots[idx + 1].val;
        // 这里low & 0xFFFFFFFFL是为了将low转变为无符号long，否则当low是负数时和long进行或运算时会将low用符号位补齐为long
        return (long) high << 32 | (low & 0xFFFFFFFFL);
    }

    public void setDouble(int idx, double val) {
        setLong(idx, (long) val);
    }

    // double占用两个slot
    public double getDouble(int idx) {
        return (double) getLong(idx);
    }

    public void setRef(int idx, Object ref) {
        slots[idx].ref = ref;
    }

    public Object getRef(int idx) {
        return slots[idx].ref;
    }

    public Slot[] getSlots() {
        return this.slots;
    }

    public void setSlot(int idx, Slot slot) {
        slots[idx] = slot;
    }

    public void print() {
        System.out.println("+----------local variable table----------");
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].ref != null) {
                System.out.println("|    Ref:slot[" + i + "]=" + slots[i].ref.getClazz().getName());
            } else {
                System.out.println("|    Value:slot[" + i + "]=" + slots[i].val);
            }
        }
        System.out.println("+----------local variable table----------");
    }
}
