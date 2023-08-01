package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description:
 *  // InnerClasses属性用于记录内部类与宿主类之间的关联。
 * InnerClasses_attribute {
 *  // 该索引处存放CONSTANT_Utf8_info常量项，描述字符串“InnerClass”。
 *  u2 attribute_name_index;
 *  u4 attribute_length;
 *  u2 number_of_classes;
 *  { u2 inner_class_info_index;
 *    u2 outer_class_info_index;
 *    u2 inner_name_index;
 *    u2 inner_class_access_flags;
 *  } classes[number_of_classes];
 * }
 * @author zhaooo3
 * @date 4/26/23 4:06 PM
 */
public class InnerClassesAttribute implements AttributeInfo {

    private InnerClassInfo[] classes;

    @Override
    public void readInfo(ClassReader reader) {
        int numberOfClasses = reader.readU2();
        classes = new InnerClassInfo[numberOfClasses];
        for (int i = 0; i < numberOfClasses; i++) {
            classes[i] = new InnerClassInfo(reader.readU2(), reader.readU2(), reader.readU2(), reader.readU2());
        }
    }

    static class InnerClassInfo {

        private int innerClassInfoIndex;
        private int outerClassInfoIndex;
        private int innerNameIndex;
        private int innerClassAccessFlags;

        InnerClassInfo(int innerClassInfoIndex, int outerClassInfoIndex, int innerNameIndex, int innerClassAccessFlags) {
            this.innerClassInfoIndex = innerClassInfoIndex;
            this.outerClassInfoIndex = outerClassInfoIndex;
            this.innerNameIndex = innerNameIndex;
            this.innerClassAccessFlags = innerClassAccessFlags;
        }

    }

}
