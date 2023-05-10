package cn.zhaooo.jvm.rtda.jvmstack;

import cn.zhaooo.jvm.rtda.heap.methodarea.Object;

/**
 * @description: java栈帧的slot是指栈帧中的局部变量数组的一个单元
 * 每个slot占4个字节，可以存储任意类型的数据。如果数据类型超过4个字节，比如long或double，那么需要两个slot来存储
 * @author zhaooo3
 * @date 5/9/23 3:34 PM
 */
public class Slot {
    //  整数
    public int num;
    //  引用
    public Object ref;
}
