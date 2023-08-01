package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @author zhaooo3
 * @description: 在JDK 5引入泛型之后，LocalVariableTable属性增加了一个“姐妹属性”——LocalVariableTypeTable。
 * 这个新增的属性结构与LocalVariableTable非常相似，仅仅是把记录的字段描述符的descriptor_index替换成了字段的特征签名（Signature）。
 * 对于非泛型类型来说，描述符和特征签名能描述的信息是能吻合一致的，但是泛型引入之后，由于描述符中泛型的参数化类型被擦除掉，描述符就不能准确描述泛型类型了。
 * 因此出现了LocalVariableTypeTable属性，使用字段的特征签名来完成泛型的描述。
 * LocalVariableTypeTable_attribute {
 *  // 源文件名索引，指向CONSTANT_Utf8_info常量"LocalVariableTypeTable"
 *  u2 attribute_name_index;
 *  u4 attribute_length;
 *  u2 local_variable_type_table_length;
 *  {   u2 start_pc;
 *      u2 length;
 *      // 常量池CONSTANT_Utf81nfo索引，用来表示一个有效的非限定名，以指代这个局部变量
 *      u2 name_index;
 *      // 常量池CONSTANT_Utf81nfo索引，此结构是个用来表示源程序中局部变量类型的字段签名
 *      u2 signature_index;
 *      u2 index;
 *      // index为此局部变量在当前栈帧的局部变量表中的索引。如果在index索引处的局部变量是long或double类型，则占用index和index+1两个位置。
 *  } local_variable_type_table[local_variable_type_table_length];
 * }
 * @date 5/4/23 11:12 AM
 */
public class LocalVariableTypeTableAttribute implements AttributeInfo {

    private LocalVariableTypeTableEntry[] localVariableTypeTable;

    @Override
    public void readInfo(ClassReader reader) {
        int localVariableTypeTableLength = reader.readU2();
        localVariableTypeTable = new LocalVariableTypeTableEntry[localVariableTypeTableLength];
        for (int i = 0; i < localVariableTypeTableLength; i++) {
            localVariableTypeTable[i] = new LocalVariableTypeTableEntry(reader.readU2(), reader.readU2(), reader.readU2(), reader.readU2(), reader.readU2());
        }
    }

    static class LocalVariableTypeTableEntry {

        private int startPC;
        private int length;
        private int nameIndex;
        private int signatureIndex;
        private int index;

        public LocalVariableTypeTableEntry(int startPC, int length, int nameIndex, int signatureIndex, int index) {
            this.startPC = startPC;
            this.length = length;
            this.nameIndex = nameIndex;
            this.signatureIndex = signatureIndex;
            this.index = index;
        }
    }
}
