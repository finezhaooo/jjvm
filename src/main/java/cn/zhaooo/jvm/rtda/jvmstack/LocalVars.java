package cn.zhaooo.jvm.rtda.jvmstack;

import cn.zhaooo.jvm.rtda.heap.methodarea.Object;

/**
 * @description: 局部遍历表
 * @author zhaooo3
 * @date 5/9/23 3:34 PM
 */
public class LocalVars {
    // 保存基本类型；对象引用（reference类型）；returnAddress类型（直线一条字节码地址）
    private Slot[] slots;

    // 编译期确定，保存在当前方法code属性的maximum local variables数据项
    public LocalVars(int maxLocals) {
        if (maxLocals > 0) {
            slots = new Slot[maxLocals];
            for (int i = 0; i < maxLocals; i++) {
                slots[i] = new Slot();
            }
        }
    }

    public void setInt(int idx, int val) {
        slots[idx].num = val;
    }

    public int getInt(int idx) {
        return slots[idx].num;
    }

    public void setFloat(int idx, float val) {
        slots[idx].num = (Float.valueOf(val)).intValue();
    }

    public Float getFloat(int idx) {
        int num = slots[idx].num;
        return (float) num;
    }

    public void setLong(int idx, long val) {
        slots[idx].num = (int) val;
        slots[idx + 1].num = (int) (val >> 32);
    }

    public Long getLong(int idx) {
        int low = slots[idx].num;
        int high = slots[idx + 1].num;
        return ((long) high << 32) | (long) low;
    }

    public void setDouble(int idx, double val) {
        setLong(idx, (long) val);
    }

    public Double getDouble(int idx) {
        return Double.valueOf(getLong(idx));
    }

    public void setRef(int idx, Object ref) {
        slots[idx].ref = ref;
    }

    public Object getRef(int idx) {
        return slots[idx].ref;
    }

    public Slot[] getSlots() {
        return slots;
    }
}
