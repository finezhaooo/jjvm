package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description:
 * // BootstrapMethods属性是变长属性，位于ClassFile结构的属性表中。它用于保存由invokedynamic指令引用的引导方法限定符。
 * // 如果某个ClassFi1e结构的常量池表中有至少一个CONSTANT_InvokeDynamic_info成员，那么这个classFi1e结构的属性表就必须包含，且只能包含一个BootstrapMethods属性。
 * // classFile结构的属性表中最多只能有一个BootstrapMethods属性。
 * BootstrapMethods_attribute {
 *  u2 attribute_name_index;
 *  u4 attribute_length;
 *  u2 num_bootstrap_methods;
 *  { u2 bootstrap_method_ref;
 *    u2 num_bootstrap_arguments;
 *    u2 bootstrap_arguments[num_bootstrap_arguments];
 *  } bootstrap_methods[num_bootstrap_methods];
 * }
 * @author zhaooo3
 * @date 5/4/23 11:25 AM
 */
public class BootstrapMethodsAttribute implements AttributeInfo {

    BootstrapMethod[] bootstrapMethods;

    @Override
    public void readInfo(ClassReader reader) {
        int bootstrapMethodNum = reader.readU2();
        bootstrapMethods = new BootstrapMethod[bootstrapMethodNum];
        for (int i = 0; i < bootstrapMethodNum; i++) {
            bootstrapMethods[i] = new BootstrapMethod(reader.readU2(), reader.readU2s());
        }
    }

    static class BootstrapMethod {

        int bootstrapMethodRef;
        int[] bootstrapArguments;

        BootstrapMethod(int bootstrapMethodRef, int[] bootstrapArguments) {
            this.bootstrapMethodRef = bootstrapMethodRef;
            this.bootstrapArguments = bootstrapArguments;
        }
    }
}
