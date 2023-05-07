package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @description:
 * The CONSTANT_InvokeDynamic_info structure is used by an invokedynamic instruction (§invokedynamic) to specify
 * a bootstrap method, the dynamic invocation name, the argument and return types of the call, and optionally,
 * a sequence of additional constants called static arguments to the bootstrap method.
 * CONSTANT_InvokeDynamic_info {
 *      u1 tag;
 *      // The value of the bootstrap_method_attr_index item must be a valid index to the bootstrap methods array of
 *      // the bootstrap method table in the current class file.
 *      u2 bootstrap_method_attr_index;
 *      // The CONSTANT_NameAndType_info index of the constant pool table
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
        return CONSTANT_INVOKEDYNAMIC;
    }

}
