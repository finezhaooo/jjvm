package cn.zhaooo.jvm.rdta.heap.methodarea;

import cn.zhaooo.jvm.rdta.jvmstack.Slot;

/**
 * @ClassName: Slots
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/08/07 17:13
 */
public class Slots {
    private Slot[] slots;

    public Slots(int slotCount) {
        if (slotCount > 0) {
            slots = new Slot[slotCount];
            for (int i = 0; i < slotCount; i++) {
                slots[i] = new Slot();
            }
        }
    }

    public void setInt(int idx, int val) {
        slots[idx].val = val;
    }

    public int getInt(int idx) {
        return slots[idx].val;
    }

    public void setFloat(int idx, float val) {
        slots[idx].val = (int) val;
    }

    public float getFloat(int idx) {
        return slots[idx].val;
    }

    public void setLong(int idx, long val) {
        slots[idx].val = (int) val;
        slots[idx + 1].val = (int) (val >> 32);
    }

    public long getLong(int idx) {
        int low = slots[idx].val;
        int high = slots[idx + 1].val;
        return (long) high << 32 | (low & 0xFFFFFFFFL);
    }

    public void setDouble(int idx, double val) {
        setLong(idx, (long) val);
    }

    public Double getDouble(int idx) {
        return (double) getLong(idx);
    }

    public void setRef(int idx, Object ref) {
        slots[idx].ref = ref;
    }

    public Object getRef(int idx){
        return slots[idx].ref;
    }
}
