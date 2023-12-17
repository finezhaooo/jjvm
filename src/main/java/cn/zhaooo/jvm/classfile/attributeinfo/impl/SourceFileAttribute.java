package cn.zhaooo.jvm.classfile.attributeinfo.impl;

import cn.zhaooo.jvm.classfile.ClassReader;
import cn.zhaooo.jvm.classfile.ConstantPool;
import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description:
 * SourceFile_attribute {
 *   u2 attribute_name_index;
 *   u4 attribute_length;
 *   // 源文件名索引，指向CONSTANT_Utf8_info常量
 *   u2 sourcefile_index;
 * }
 * @author zhaooo3
 * @date 4/26/23 8:21 PM
 */
public class SourceFileAttribute implements AttributeInfo {

    private ConstantPool constantPool;
    private int sourceFileIdx;

    public SourceFileAttribute(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        //  attribute_length的值必须是2
        sourceFileIdx = reader.readU2();
    }

    public String getFileName() {
        return constantPool.getUTF8(sourceFileIdx);
    }

}
