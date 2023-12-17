package cn.zhaooo.jvm.classfile;

import cn.zhaooo.jvm.classfile.attributeinfo.AttributeInfo;
import cn.zhaooo.jvm.classfile.attributeinfo.impl.CodeAttribute;
import cn.zhaooo.jvm.classfile.attributeinfo.impl.ConstantValueAttribute;

/**
 * @description:
 * 一个Java类的字段和方法有相似的地位和作用，故在字节码中格式也类似，同一用MemberInfo表示
 * field_info {
 *    u2 access_flags;
 *    u2 name_index;
 *    u2 descriptor_index;
 *    u2 attributes_count;
 *    // ConstantValue（常量值属性）
 *    // Signature（签名属性）
 *    // Synthetic（合成属性）
 *    // Deprecated（废弃属性）
 *    // RuntimeVisibleAnnotations 和 RuntimeInvisibleAnnotations（可见注解属性）
 *    // RuntimeVisibleTypeAnnotations 和 RuntimeInvisibleTypeAnnotations（可见类型注解属性）
 *    // BootstrapMethods（引导方法属性）
 *    attribute_info attributes[attributes_count];
 * }
 * method_info {
 *    u2 access_flags;
 *    u2 name_index;
 *    u2 descriptor_index;
 *    u2 attributes_count;
 *    // Code（代码属性）
 *    // Exceptions（异常表属性）
 *    // Signature（签名属性）
 *    // Synthetic（合成属性）
 *    // Deprecated（废弃属性）
 *    // RuntimeVisibleAnnotations 和 RuntimeInvisibleAnnotations（可见注解属性）
 *    // RuntimeVisibleTypeAnnotations 和 RuntimeInvisibleTypeAnnotations（可见类型注解属性）
 *    // MethodParameters（方法参数属性）
 *    attribute_info attributes[attributes_count];
 * }
 * @author zhaooo3
 * @date 4/24/23 12:37 AM
 */
public class MemberInfo {
    // 常量池
    private ConstantPool constantPool;
    // 访问标志
    private int accessFlags;
    // 字段/方法名字的常量池索引
    private int nameIdx;
    // 字段/方法描述符的常量池索引
    private int descriptorIdx;
    // 属性表
    private AttributeInfo[] attributes;

    private MemberInfo(ClassReader reader, ConstantPool constantPool) {
        this.constantPool = constantPool;
        this.accessFlags = reader.readU2();
        this.nameIdx = reader.readU2();
        this.descriptorIdx = reader.readU2();
        this.attributes = AttributeInfo.read(reader, constantPool);
    }

    /**
     * 读取字段表或方法表
     */
    static MemberInfo[] readMembers(ClassReader reader, ConstantPool constantPool) {
        int memberCount = reader.readU2();
        MemberInfo[] members = new MemberInfo[memberCount];
        for (int i = 0; i < memberCount; i++) {
            members[i] = new MemberInfo(reader, constantPool);
        }
        return members;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    /**
     * 从常量池查找字段或方法名
     */
    public String getName() {
        return constantPool.getUTF8(nameIdx);
    }

    /**
     * 从常量池查找字段或方法描述符
     */
    public String getDescriptor() {
        return constantPool.getUTF8(descriptorIdx);
    }

    /**
     * 从属性表查找Code属性
     */
    public CodeAttribute getCodeAttribute() {
        for (AttributeInfo attrInfo : attributes) {
            if (attrInfo instanceof CodeAttribute) {
                return (CodeAttribute) attrInfo;
            }
        }
        return null;
    }

    /**
     * 从属性表查找ConstantValue属性
     * @return
     */
    public ConstantValueAttribute getConstantValueAttribute() {
        for (AttributeInfo attrInfo : attributes) {
            if (attrInfo instanceof ConstantValueAttribute) {
                return (ConstantValueAttribute) attrInfo;
            }
        }
        return null;
    }
}
