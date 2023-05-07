package cn.zhaooo.jvm.classfile;

import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;

/**
 * @description:
 * ClassFile {
 *  u4 magic;
 *  u2 minor_version;
 *  u2 major_version;
 *  u2 constant_pool_count;
 *  cp_info constant_pool[constant_pool_count-1];
 *  u2 access_flags;
 *  u2 this_class;
 *  u2 super_class;
 *  u2 interfaces_count;
 *  u2 interfaces[interfaces_count];
 *  u2 fields_count;
 *  field_info fields[fields_count];
 *  u2 methods_count;
 *  method_info methods[methods_count];
 *  u2 attributes_count;
 *  attribute_info attributes[attributes_count];
 * }
 * @author zhaooo3
 * @date 4/24/23 12:37 AM
 */
public class ClassFile {
    int minorVersion;               //  次版本号
    int majorVersion;               //  主版本号
    ConstantPool constantPool;      //  常量池
    int accessFlags;                //  类访问标志
    int thisClassIdx;               //  类索引
    int superClassIdx;              //  超类索引
    int[] interfaces;               //  接口索引表
    MemberInfo[] fields;            //  字段表
    MemberInfo[] methods;           //  方法表
    AttributeInfo[] attributes;     //  属性表

    public ClassFile(byte[] classData) {
        ClassReader reader = new ClassReader(classData);
        readAndCheckMagic(reader);
        readAndCheckVersion(reader);
        constantPool = readConstantPool(reader);
        accessFlags = reader.readU2();
        thisClassIdx = reader.readU2();
        superClassIdx = reader.readU2();
        interfaces = reader.readU2s();
        fields = MemberInfo.readMembers(reader, constantPool);
        methods = MemberInfo.readMembers(reader, constantPool);
        attributes = AttributeInfo.read(reader, constantPool);
    }

    /**
     * 读取并校验魔数
     */
    private void readAndCheckMagic(ClassReader reader) {
        //  class文件的魔数是"0xCAFEBABE"
        long magic = reader.readU4();
        if (magic != (0xCAFEBABEL & 0x0FFFFFFFFL)) {
            //  Java虚拟机规范规定，如果加载的class文件不符合要求的格式，Java虚拟机实现就抛出java.lang.ClassFormatError异常
            throw new ClassFormatError("magic!");
        }
    }

    /**
     * 读取并校验版本号
     */
    private void readAndCheckVersion(ClassReader reader) {
        //  次版本号和主版本号都是u2类型
        minorVersion = reader.readU2();
        majorVersion = reader.readU2();

        //  Java SE 8支持版本号为45.0~52.0的class文件
        switch (this.majorVersion) {
            case 45:
                return;
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
                if (this.minorVersion == 0) return;
        }

        //  如果版本号不在支持的范围内，Java虚拟机实现就抛出java.lang.UnsupportedClassVersionError异常
        throw new UnsupportedClassVersionError();
    }

    private ConstantPool readConstantPool(ClassReader reader) {
        return new ConstantPool(reader);
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public String getClassName() {
        return constantPool.getClassName(thisClassIdx);
    }

    public String getSuperClassName() {
        //  除java.lang.Object之外，其他类都有超类，所以supperClass只在Object.class中是0，在其他class文件中必须是有效的常量池索引
        if (superClassIdx <= 0) {
            return "";
        }
        return this.constantPool.getClassName(superClassIdx);
    }

    public String[] getInterfaceNames() {
        String[] interfaceNames = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            interfaceNames[i] = constantPool.getClassName(interfaces[i]);
        }
        return interfaceNames;
    }

    public MemberInfo[] getFields() {
        return fields;
    }

    public MemberInfo[] getMethods() {
        return methods;
    }
}
