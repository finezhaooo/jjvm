package cn.zhaooo.jvm.classfile.constantInfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;

/**
 * @description:
 * 用于表示方法类型的 CONSTANT_MethodType_info 结构:
 * CONSTANT_MethodType_info {
 *      u1 tag;
 *      // 指向常量池中的 CONSTANT_Utf8_info 常量的索引，该常量包含方法的描述符。方法的描述符描述了方法的参数类型和返回类型。
 *      // 方法描述符即descriptor: (参数类型1, 参数类型2, ...)返回类型,例如(I)Ljava/lang/String
 *      u2 descriptor_index;
 * }
 * @author zhaooo3
 * @date 4/24/23 12:13 AM
 */
public class ConstantMethodTypeInfo implements ConstantInfo {

    private int descriptorIdx;

    @Override
    public void readInfo(ClassReader reader) {
        descriptorIdx = reader.readU2();
    }

    @Override
    public int getTag() {
        return CONSTANT_METHOD_TYPE;
    }

    public int getDescriptorIdx() {
        return descriptorIdx;
    }

}