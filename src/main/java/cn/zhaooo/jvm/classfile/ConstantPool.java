package cn.zhaooo.jvm.classfile;

import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;
import cn.zhaooo.jvm.classfile.constantInfo.impl.ConstantClassInfo;
import cn.zhaooo.jvm.classfile.constantInfo.impl.ConstantNameAndTypeInfo;
import cn.zhaooo.jvm.classfile.constantInfo.impl.ConstantUtf8Info;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: TODO
 * @author zhaooo3
 * @date 4/23/23 6:37 PM
 */
public class ConstantPool {
    //  常量信息表
    private final ConstantInfo[] constantInfos;
    //  常量池大小
    private final int size;

    public ConstantPool(ClassReader reader) {
        size = reader.readU2();
        constantInfos = new ConstantInfo[size];

        //  有效的常量池索引是1~n-1，0是无效索引，表示不指向任何常量
        for (int i = 1; i < size; i++) {
            constantInfos[i] = ConstantInfo.read(reader, this);
            //  CONSTANT_Long_info和CONSTANT_Double_info各占两个slot
            switch (constantInfos[i].getTag()) {
                case ConstantInfo.CONSTANT_LONG:
                case ConstantInfo.CONSTANT_DOUBLE:
                    i++;
                    break;
            }
        }
    }

    /**
     * 从常量池查找字段或方法的名字和描述符
     */
    public Map<String, String> getNameAndType(int idx) {
        ConstantNameAndTypeInfo nameAndTypeInfo = (ConstantNameAndTypeInfo) constantInfos[idx];
        Map<String, String> map = new HashMap<>();
        map.put("name", getUTF8(nameAndTypeInfo.nameIdx));
        map.put("_type", getUTF8(nameAndTypeInfo.descIdx));
        return map;
    }

    public String getClassName(int idx) {
        ConstantClassInfo classInfo = (ConstantClassInfo) constantInfos[idx];
        return getUTF8(classInfo.nameIdx);
    }

    public String getUTF8(int idx) {
        ConstantUtf8Info utf8Info = (ConstantUtf8Info) constantInfos[idx];
        return utf8Info == null ? "" : utf8Info.getStr();
    }

    public ConstantInfo[] getConstantInfos() {
        return constantInfos;
    }

    public int getSize() {
        return size;
    }
}
