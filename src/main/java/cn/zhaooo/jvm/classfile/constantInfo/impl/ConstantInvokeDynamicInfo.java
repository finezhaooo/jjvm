package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @description:
 * CONSTANT_InvokeDynamic_info 结构由 invokedynamic 指令（§invokedynamic）（0xba）使用，
 * 用于指定引导方法、动态调用名称、调用的参数和返回类型，以及可选的引导方法的静态参数序列。
 * 即在运行时动态确定方法调用的目标方法。
 * CONSTANT_InvokeDynamic_info {
 *      u1 tag;
 *      // 当前类文件中引导方法表即bootstrapMethods的有效索引。bootstrapMethods 在 BootstrapMethodsAttribute 属性中定义。
 *      u2 bootstrap_method_attr_index;
 *      // 常量池表的 CONSTANT_NameAndType_info 索引
 *      u2 name_and_type_index;
 * }
 * @author zhaooo3
 * @date 4/24/23 12:16 AM
 */

public class ConstantInvokeDynamicInfo implements ConstantInfo {

    private int bootstrapMethodAttrIdx;
    private int nameAndTypeIdx;

    @Override
    public void readInfo(ClassReader reader) {
        bootstrapMethodAttrIdx = reader.readU2();
        nameAndTypeIdx = reader.readU2();
    }

    @Override
    public int getTag() {
        return CONSTANT_INVOKE_DYNAMIC;
    }

    public int getBootstrapMethodAttrIdx() {
        return bootstrapMethodAttrIdx;
    }

    public int getNameAndTypeIdx() {
        return nameAndTypeIdx;
    }

    //TODO invokedynamic实现 参考https://zhuanlan.zhihu.com/p/625814074
}
