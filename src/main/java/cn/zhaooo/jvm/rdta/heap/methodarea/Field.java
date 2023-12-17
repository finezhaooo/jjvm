package cn.zhaooo.jvm.rdta.heap.methodarea;

import cn.zhaooo.jvm.classfile.MemberInfo;
import cn.zhaooo.jvm.classfile.attributeinfo.impl.ConstantValueAttribute;

/**
 * @ClassName: Field
 * @Description: 字段信息
 * @Author: zhaooo
 * @Date: 2023/08/07 17:11
 */
public class Field extends ClassMember {

    // 类变量的常量池索引
    public int constValueIndex;
    // 类变量的数据索引
    public int slotId;

    /**
     * 根据class文件的字段信息创建字段表
     */
    public Field[] create(Class clazz, MemberInfo[] cfFields) {
        Field[] fields = new Field[cfFields.length];
        for (int i = 0; i < cfFields.length; i++) {
            fields[i] = new Field();
            fields[i].clazz = clazz;
            fields[i].copyMemberInfo(cfFields[i]);
            fields[i].copyAttributes(cfFields[i]);
        }
        return fields;
    }

    public void copyAttributes(MemberInfo cfField) {
        ConstantValueAttribute valAttr = cfField.getConstantValueAttribute();
        if (null != valAttr) {
            constValueIndex = valAttr.getConstantValueIdx();
        }
    }

    public int getConstValueIndex() {
        return constValueIndex;
    }

    public int getSlotId() {
        return slotId;
    }

    public boolean isLongOrDouble() {
        return descriptor.equals("J") || descriptor.equals("D");
    }
}
