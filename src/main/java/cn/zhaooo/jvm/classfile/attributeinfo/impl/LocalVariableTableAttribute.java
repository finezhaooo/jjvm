package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description:
 * LocalVariableTable属性用于描述栈帧中局部变量表的变量与Java源码中定义的变量之间的关系，如果没有生成这项属性，
 * 最大的影响就是当其他人引用这个方法时，所有的参数名称都将会丢失，譬如IDE将会使用诸如arg0、arg1之类的占位符代替原有的参数名
 * 在Code属性的属性表中，多个LocalVariableTable属性可以按照任意顺序出现。Code属性attributes表中的每个局部变量，最多只能有一个LocalVariableTable属性。
 * LocalVariableTable_attribute {
 *   // 源文件名索引，指向CONSTANT_Utf8_info常量"LocalVariableTable"
 *   u2 attribute_name_index;
 *   u4 attribute_length;
 *   u2 local_variable_table_length;
 *   // local_variable table数组中的每一项，都以偏移量的形式给出了code数组中的某个范围，当局部变量处在这个范围内的时候，它是有值的。
 *   // 此项还会给出局部变量在当前帧的局部变量表（local variable array)中的索引。
 *   {  // 局部变量的生命周期开始的字节码偏移量
 *      u2 start_pc;
 *      // 作用范围覆盖的长度
 *      u2 length;
 *      // 局部变量的名称
 *      u2 name_index;
 *      // 个局部变量的描述符
 *      u2 descriptor_index;
 *      // index是这个局部变量在栈帧的局部变量表中变量槽的位置。当这个变量数据类型是64位类型时（double和long），它占用的变量槽为index和index+1两个
 *      u2 index;
 *   } local_variable_table[local_variable_table_length];
 * }
 * @author zhaooo3
 * @date 4/26/23 8:36 PM
 */
public class LocalVariableTableAttribute implements AttributeInfo {

    private LocalVariableTableEntry[] localVariableTable;

    @Override
    public void readInfo(ClassReader reader) {
        int localVariableTableLength = reader.readU2();
        localVariableTable = new LocalVariableTableEntry[localVariableTableLength];
        for (int i = 0; i < localVariableTableLength; i++) {
            localVariableTable[i] = new LocalVariableTableEntry(reader.readU2(), reader.readU2(), reader.readU2(), reader.readU2(), reader.readU2());
        }
    }

    static class LocalVariableTableEntry {

        private int startPC;
        private int length;
        private int nameIdx;
        private int descriptorIdx;
        private int idx;

        LocalVariableTableEntry(int startPC, int length, int nameIdx, int descriptorIdx, int idx) {
            this.startPC = startPC;
            this.length = length;
            this.nameIdx = nameIdx;
            this.descriptorIdx = descriptorIdx;
            this.idx = idx;
        }
    }
}