package cn.zhaooo.jvm.rdta.jvmstack;

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
        if (maxLocals > 0) {
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
}
