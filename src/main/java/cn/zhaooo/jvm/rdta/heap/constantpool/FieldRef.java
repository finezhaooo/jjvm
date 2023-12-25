package cn.zhaooo.jvm.rdta.heap.constantpool;

import cn.zhaooo.jvm.classfile.constantInfo.impl.ConstantFieldrefInfo;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Field;


import java.util.Map;

/**
 * @ClassName: FieldRef
 * @Description: 字段符号引用
 * @Author: zhaooo
 * @Date: 2023/08/07 23:42
 */
public class FieldRef extends SymRef {

    private String name;         //  字段名
    private String descriptor;   //  字段描述符
    private Field field;        //  字段

    public static FieldRef create(RunTimeConstantPool runTimeConstantPool, ConstantFieldrefInfo refInfo) {
        FieldRef ref = new FieldRef();
        ref.runTimeConstantPool = runTimeConstantPool;
        ref.copyMemberRefInfo(refInfo);
        return ref;
    }

    public void copyMemberRefInfo(ConstantFieldrefInfo fieldrefInfo) {
        className = fieldrefInfo.getClassName();
        Map<String, String> map = fieldrefInfo.getNameAndDescriptor();
        name = map.get("name");
        descriptor = map.get("_type");
    }

    /**
     * 解析字段符号引用
     */
    public Field resolvedFieldRef() {
        if (null == field) {
            Class d = runTimeConstantPool.getClazz();
            // 从class文件对应的类 解析出字段符号引用对应类
            Class c = getResolvedClass();
            Field field = lookupField(c, name, descriptor);
            if (null == field) {
                throw new NoSuchFieldError();
            }
            if (!field.isAccessibleTo(d)) {
                throw new IllegalAccessError();
            }
            this.field = field;
        }
        return field;
    }

    private Field lookupField(Class c, String name, String descriptor) {
        for (Field field : c.fields) {
            if (field.name.equals(name) && field.descriptor.equals(descriptor)) {
                return field;
            }
        }
        // 接口里面定义的成员变量都是 public static final 修饰
        for (Class iface : c.interfaces) {
            Field field = lookupField(iface, name, descriptor);
            if (null != field) return field;
        }

        if (c.superClass != null) {
            return lookupField(c.superClass, name, descriptor);
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public String getDescriptor() {
        return this.descriptor;
    }
}
