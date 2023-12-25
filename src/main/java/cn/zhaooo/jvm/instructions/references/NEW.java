package cn.zhaooo.jvm.instructions.references;

import cn.zhaooo.jvm.instructions.base.impl.Index16Instruction;
import cn.zhaooo.jvm.rdta.heap.constantpool.ClassRef;
import cn.zhaooo.jvm.rdta.heap.constantpool.RunTimeConstantPool;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
/**
 * 创建类实例，操作数是一个2字节索引，来自字节码
 */
public class NEW extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        //  从当前类的运行时常量池中找到一个类符号引用
        RunTimeConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) cp.getConstants(idx);

        //  解析类符号引用，拿到类数据
        Class clazz = classRef.getResolvedClass();
        if (clazz.isInterface() || clazz.isAbstract()) {
            //  接口和抽象类不能实例化，如果解析后的类是接口或抽象类，按照Java虚拟机规范规定，需要抛出InstantiationError异常
            throw new InstantiationError();
        }

        //  创建对象，并把对象引用推入栈顶
        Object ref = clazz.newObject();
        frame.getOperandStack().pushRef(ref);
    }

}