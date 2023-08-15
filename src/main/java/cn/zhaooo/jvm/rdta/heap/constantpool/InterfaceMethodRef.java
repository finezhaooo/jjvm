package cn.zhaooo.jvm.rdta.heap.constantpool;

import cn.zhaooo.jvm.classfile.constantInfo.impl.ConstantInterfaceMethodrefInfo;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Method;
import cn.zhaooo.jvm.rdta.heap.util.MethodLookup;

import java.util.Map;

/**
 * @ClassName: InterfaceMethodRef
 * @Description: 接口方法符号引用
 * @Author: zhaooo
 * @Date: 2023/08/07 23:47
 */
public class InterfaceMethodRef extends SymRef {

    public String name;         //  接口方法名
    public String descriptor;   //  接口方法描述符
    private Method method;      //  接口方法

    /**
     * 创建接口方法符号引用
     */
    public static InterfaceMethodRef create(RunTimeConstantPool runTimeConstantPool, ConstantInterfaceMethodrefInfo refInfo) {
        InterfaceMethodRef ref = new InterfaceMethodRef();
        ref.runTimeConstantPool = runTimeConstantPool;
        ref.copyMemberRefInfo(refInfo);
        return ref;
    }

    public void copyMemberRefInfo(ConstantInterfaceMethodrefInfo interfaceMethodrefInfo) {
        className = interfaceMethodrefInfo.getClassName();
        Map<String, String> map = interfaceMethodrefInfo.getNameAndDescriptor();
        name = map.get("name");
        descriptor = map.get("_type");
    }

    /**
     * 解析接口方法符号引用
     */
    public Method resolvedInterfaceMethodRef() {
        if (method == null) {
            Class d = runTimeConstantPool.getClazz();
            Class c = resolvedClass();
            if (!c.isInterface()) {
                throw new IncompatibleClassChangeError();
            }

            Method method = lookupInterfaceMethod(c, name, descriptor);
            if (null == method) {
                throw new NoSuchMethodError();
            }

            if (!method.isAccessibleTo(d)) {
                throw new IllegalAccessError();
            }

            this.method = method;
        }
        return method;
    }

    /**
     * 根据接口方法名和描述符查找接口方法
     */
    private Method lookupInterfaceMethod(Class iface, String name, String descriptor) {
        for (Method method : iface.methods) {
            if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
                return method;
            }
        }
        return MethodLookup.lookupMethodInInterfaces(iface.interfaces, name, descriptor);
    }

    public String getName() {
        return this.name;
    }

    public String getDescriptor() {
        return this.descriptor;
    }

}
