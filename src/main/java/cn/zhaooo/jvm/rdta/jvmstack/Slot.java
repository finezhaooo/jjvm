package cn.zhaooo.jvm.rdta.jvmstack;

/**
 * @ClassName: Slot
 * @Description: 数据槽
 * @Author: zhaooo
 * @Date: 2023/08/02 21:45
 */
public class Slot {
    // 保存boolean、byte、char、short、int、float、long、double
    // 拓展boolean、byte、char、short为int
    // long double 要用两个Slot
    int val;
    // 保存对象引用
    // 也可以使用句柄和句柄池保存
    Object ref;
}
