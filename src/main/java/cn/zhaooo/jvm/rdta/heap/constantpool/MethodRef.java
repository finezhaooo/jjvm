package cn.zhaooo.jvm.rdta.heap.constantpool;

import cn.zhaooo.jvm.classfile.constantInfo.impl.ConstantMethodrefInfo;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Method;
import cn.zhaooo.jvm.rdta.heap.util.MethodLookup;

import java.util.Map;

/**
 * @ClassName: MethodRef
 * @Description: 方法符号引用
 * @Author: zhaooo
 * @Date: 2023/08/07 23:43
 */
public class MethodRef extends SymRef {

    private String name;         //  方法名
    private String descriptor;   //  方法描述符
    private Method method;      //  方法

    /**
     * 创建方法符号引用
     */
    public static MethodRef create(RunTimeConstantPool runTimeConstantPool, ConstantMethodrefInfo refInfo) {
        MethodRef ref = new MethodRef();
        ref.runTimeConstantPool = runTimeConstantPool;
        ref.copyMemberRefInfo(refInfo);
        return ref;
    }

    public void copyMemberRefInfo(ConstantMethodrefInfo methodrefInfo) {
        className = methodrefInfo.getClassName();
        Map<String, String> map = methodrefInfo.getNameAndDescriptor();
        name = map.get("name");
        descriptor = map.get("_type");
    }

    /**
     * 解析方法符号引用
     */
    public Method resolvedMethodRef() {
        if (null == method) {
            //  如果类d想通过方法符号引用访问类c的某个方法，先要解析符号引用得到类c
            Class d = runTimeConstantPool.getClazz();
            Class c = getResolvedClass();

            //  如果c是接口，则抛出IncompatibleClassChangeError
            if (c.isInterface()) {
                throw new IncompatibleClassChangeError();
            }

            //  如果c不是接口，根据方法名和描述符查找方法
            Method method = lookupMethod(c, name, descriptor);
            //  如果找不到对应的方法，抛出NoSuchMethodError
            if (null == method) {
                throw new NoSuchMethodError();
            }
            //  检查类d是否有权限访问该方法
            if (!method.isAccessibleTo(d)) {
                //  如果没有，抛出IllegalAccessError
                throw new NoSuchMethodError();
            }
            this.method = method;
        }
        return method;
    }

    /**
     * 根据方法名和描述符查找方法
     */
    public Method lookupMethod(Class clazz, String name, String descriptor) {
        //  先从c的继承中找
        Method method = MethodLookup.lookupMethodInClass(clazz, name, descriptor);
        //  如果找不到，就去c的接口中找
        if (null == method) {
            method = MethodLookup.lookupMethodInInterfaces(clazz.interfaces, name, descriptor);
        }
        return method;
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }
}
