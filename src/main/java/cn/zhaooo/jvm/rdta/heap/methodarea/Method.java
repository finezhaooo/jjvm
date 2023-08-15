package cn.zhaooo.jvm.rdta.heap.methodarea;

import cn.zhaooo.jvm.classfile.MemberInfo;
import cn.zhaooo.jvm.classfile.attributeinfo.impl.CodeAttribute;
import cn.zhaooo.jvm.rdta.heap.constantpool.AccessFlags;
import cn.zhaooo.jvm.rdta.heap.util.MethodDescriptorParser;

import java.util.List;

/**
 * @ClassName: Method
 * @Description: 方法信息
 * @Author: zhaooo
 * @Date: 2023/08/07 17:17
 */
public class Method extends ClassMember {

    public int maxStack;        //  操作数栈大小
    public int maxLocals;       //  局部变量表大小
    public byte[] code;         //  方法字节码
    private int argSlotCount;   //  参数在局部变量表中占用的位置即args_size

    /**
     * 根据class文件中的方法信息创建方法表
     */
    public Method[] create(Class clazz, MemberInfo[] cfMethods) {
        Method[] methods = new Method[cfMethods.length];
        for (int i = 0; i < cfMethods.length; i++) {
            methods[i] = new Method();
            methods[i].clazz = clazz;
            methods[i].copyMemberInfo(cfMethods[i]);
            methods[i].copyAttributes(cfMethods[i]);
            methods[i].calcArgSlotCount();
        }
        return methods;
    }

    /**
     * 从method_info结构中提取信息
     */
    public void copyAttributes(MemberInfo cfMethod) {
        CodeAttribute codeAttr = cfMethod.getCodeAttribute();
        if (null != codeAttr) {
            maxStack = codeAttr.getMaxStack();
            maxLocals = codeAttr.getMaxLocals();
            code = codeAttr.getData();
        }
    }

    private void calcArgSlotCount() {
        // 参数和返回值类型
        MethodDescriptor parsedDescriptor = MethodDescriptorParser.parseMethodDescriptorParser(descriptor);
        List<String> parameterTypes = parsedDescriptor.parameterTypes;
        for (String paramType : parameterTypes) {
            argSlotCount++;
            //  long和double类型参数要占用两个位置，
            if ("J".equals(paramType) || "D".equals(paramType)) {
                argSlotCount++;
            }
        }
        //  对于实例方法，Java编译器会在参数列表的前面添加一个参数，这个隐藏的参数就是this引用
        if (!isStatic()) {
            argSlotCount++;
        }
    }

    public boolean isSynchronized() {
        return 0 != (accessFlags & AccessFlags.ACC_SYNCHRONIZED);
    }

    public boolean isBridge() {
        return 0 != (accessFlags & AccessFlags.ACC_BRIDGE);
    }

    public boolean isVarargs() {
        return 0 != (accessFlags & AccessFlags.ACC_VARARGS);
    }

    public boolean isNative() {
        return 0 != (accessFlags & AccessFlags.ACC_NATIVE);
    }

    public boolean isAbstract() {
        return 0 != (accessFlags & AccessFlags.ACC_ABSTRACT);
    }

    public boolean isStrict() {
        return 0 != (accessFlags & AccessFlags.ACC_STRICT);
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public byte[] getCode() {
        return code;
    }

    public int getArgSlotCount() {
        return argSlotCount;
    }
}