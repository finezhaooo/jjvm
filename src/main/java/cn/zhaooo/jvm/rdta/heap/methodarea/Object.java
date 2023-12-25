package cn.zhaooo.jvm.rdta.heap.methodarea;

/**
 * @ClassName: Object
 * @Description: 实例化的普通对象或者数组对象
 * @Author: zhaooo
 * @Date: 2023/08/07 17:12
 */
public class Object {
    Class clazz;
    java.lang.Object data;   //  对于普通对象来说，data字段中存放Slots变量，对于数组，可以在其中放各种类型的数组

    public Object(Class clazz){
        this.clazz = clazz;
        this.data = new Slots(clazz.getInstanceSlotCount());
    }

    public Object(Class clazz, java.lang.Object data) {
        this.clazz = clazz;
        this.data = data;
    }



    public boolean isInstanceOf(Class clazz) {
        return clazz.isAssignableFrom(clazz);
    }

    public Class getClazz() {
        return clazz;
    }

    public Slots getFields() {
        return (Slots) data;
    }

    // 获得当前类的field的值
    public Object getRefVar(String name, String descriptor) {
        Field field = clazz.getField(name, descriptor, false);
        Slots slots = (Slots) data;
        return slots.getRef(field.slotId);
    }

    public void setRefVal(String name, String descriptor, Object ref) {
        Field field = clazz.getField(name, descriptor, false);
        Slots slots = (Slots) data;
        slots.setRef(field.slotId, ref);
    }


    public byte[] getBytes() {
        return (byte[]) data;
    }

    public short[] getShorts() {
        return (short[]) data;
    }

    public int[] getInts() {
        return (int[]) data;
    }

    public long[] getLongs() {
        return (long[]) data;
    }

    public char[] getChars() {
        return (char[]) data;
    }

    public float[] getFloats() {
        return (float[]) data;
    }

    public double[] getDoubles() {
        return (double[]) data;
    }

    public Object[] getRefs() {
        return (Object[]) data;
    }

    /**
     * 获取数组长度
     */
    public int getArrayLength() {
        if (data instanceof byte[]) {
            return ((byte[]) data).length;
        }

        if (data instanceof short[]) {
            return ((short[]) data).length;
        }

        if (data instanceof int[]) {
            return ((int[]) data).length;
        }

        if (data instanceof long[]) {
            return ((long[]) data).length;
        }

        if (data instanceof char[]) {
            return ((char[]) data).length;
        }

        if (data instanceof float[]) {
            return ((float[]) data).length;
        }

        if (data instanceof double[]) {
            return ((double[]) data).length;
        }

        if (data instanceof Object[]) {
            return ((Object[]) data).length;
        }

        throw new RuntimeException("Not array");
    }
}
