package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description:
 * 它被调试器用于确定源文件中由给定的行号所表示的内容，对应于Java虚拟机code[]数组中的哪一部分。
 * 在Code属性attributes表中，可以有不止一个LineNumberTable属性对应于源文件中的同一行。
 * LineNumberTable_attribute {
 *  u2 attribute_name_index;
 *  u4 attribute_length;
 *  u2 line_number_table_length;
 *    // start pc项的值必须是code[]数组的一个索引，code[]在该索引处的指令码，表示源文件中新的行的起点。
 *  { u2 start_pc;
 *    // line number项的值必须与源文件中对应的行号相匹配。
 *    u2 line_number;
 *  } line_number_table[line_number_table_length];
 * }
 * @author zhaooo3
 * @date 4/26/23 8:28 PM
 */
public class LineNumberTableAttribute implements AttributeInfo {

    private LineNumberTableEntry[] lineNumberTable;

    @Override
    public void readInfo(ClassReader reader) {
        int lineNumberTableLength = reader.readU2();
        lineNumberTable = new LineNumberTableEntry[lineNumberTableLength];
        for (int i = 0; i < lineNumberTableLength; i++) {
            lineNumberTable[i] = new LineNumberTableEntry(reader.readU2(), reader.readU2());
        }
    }

    public int getLineNumber(int pc) {
        for (int i = lineNumberTable.length - 1; i >= 0; i--) {
            LineNumberTableEntry entry = lineNumberTable[i];
            if (pc >= entry.startPC) {
                return entry.lineNumber;
            }
        }
        return -1;
    }

    static class LineNumberTableEntry {

        private int startPC;
        private int lineNumber;

        LineNumberTableEntry(int startPC, int lineNumber) {
            this.startPC = startPC;
            this.lineNumber = lineNumber;
        }
    }
}