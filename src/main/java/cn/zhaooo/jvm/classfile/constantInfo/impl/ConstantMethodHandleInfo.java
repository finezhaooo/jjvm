package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @description:
 * CONSTANT_MethodHandle_info 结构用于表示 Java class 文件中的方法句柄（Method Handle）。
 * 方法句柄是 Java 7 引入的一项特性，它提供了一种更灵活的方式来操作方法引用，可以在运行时动态调用方法。
 * CONSTANT_MethodHandle_info {
 *      u1 tag;
 *      // reference_kind 项的值必须在 1 到 9（包括1和9）的范围内，
 *      // 表示方法句柄的类型（种类）。方法句柄类型确定了句柄的字节码行为
 *      u1 reference_kind;
 *      // 是常量池表的一个有效索引，指向一个方法句柄引用的目标。目标可以是一个字段、一个方法、或者一个构造方法的符号引用。
 *      u2 reference_index;
 * }
 * @author zhaooo3
 * @date 4/24/23 12:09 AM
 */
public class ConstantMethodHandleInfo implements ConstantInfo {

    /**
     * {@code referenceKind} 字段的值:
     * - {@code REF_getField}          (1): 获取字段。
     * - {@code REF_getStatic}         (2): 获取静态字段。
     * - {@code REF_putField}          (3): 设置字段。
     * - {@code REF_putStatic}         (4): 设置静态字段。
     * - {@code REF_invokeVirtual}     (5): 调用虚拟方法。
     * - {@code REF_invokeStatic}      (6): 调用静态方法。
     * - {@code REF_invokeSpecial}     (7): 调用特殊方法（例如，私有方法、构造方法）。
     * - {@code REF_newInvokeSpecial}  (8): 创建新实例并调用特殊方法（构造方法）。
     * - {@code REF_invokeInterface}   (9): 调用接口方法。
     */
    private int referenceKind;
    private int referenceIdx;

    @Override
    public void readInfo(ClassReader reader) {
        referenceKind = reader.readU1();
        referenceIdx = reader.readU2();
    }

    @Override
    public int getTag() {
        return CONSTANT_METHOD_HANDLE;
    }

    public int getReferenceKind() {
        return referenceKind;
    }

    public int getReferenceIdx() {
        return referenceIdx;
    }

}
