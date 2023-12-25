package cn.zhaooo.jvm.instructions.references;

import cn.zhaooo.jvm.instructions.base.BytecodeReader;
import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.instructions.base.MethodInvokeLogic;
import cn.zhaooo.jvm.rdta.heap.constantpool.InterfaceMethodRef;
import cn.zhaooo.jvm.rdta.heap.constantpool.RunTimeConstantPool;
import cn.zhaooo.jvm.rdta.heap.methodarea.Method;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;
import cn.zhaooo.jvm.rdta.heap.util.MethodLookup;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
/**
 * 调用接口方法
 */
public class INVOKE_INTERFACE implements Instruction {

    private int idx;    //  运行时常量池索引

    @Override
    public void fetchOperands(BytecodeReader reader) {
        //  在字节码中，invokeinterface指令的操作码后面跟着4字节而非2字节
        idx = reader.readShort();
        //  该方法床底参数需要的slot数，其含义和Method结构体定义的argSlotCount字段相同
        reader.readByte();
        //  留给Oracle的某些Java虚拟机实现用的，值必须是0
        reader.readByte();
    }

    @Override
    public void execute(Frame frame) {
        //  从运行时常量池中拿到并解析接口方法符号引用
        RunTimeConstantPool runTimeConstantPool = frame.getMethod().getClazz().getConstantPool();
        InterfaceMethodRef methodRef = (InterfaceMethodRef) runTimeConstantPool.getConstants(idx);
        Method resolvedMethod = methodRef.resolvedInterfaceMethodRef();

        //  如果解析后的方法是静态方法或私有方法，则抛出IncompatibleClassChangeError异常
        if (resolvedMethod.isStatic() || resolvedMethod.isPrivate()) {
            throw new IncompatibleClassChangeError();
        }

        //  从操作数栈中弹出this引用
        Object ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        //  如果引用时null，则抛出NullPointerException异常
        if (null == ref) {
            throw new NullPointerException();
        }

        //  如果引用所指对象的类没有实现解析出来的接口，则抛出IncompatibleClassChangeError异常
        if (!ref.getClazz().isImplements(methodRef.getResolvedClass())) {
            throw new IncompatibleClassChangeError();
        }

        //  查找最终要调用的方法
        Method methodToBeInvoked = MethodLookup.lookupMethodInClass(ref.getClazz(), methodRef.getName(), methodRef.getDescriptor());
        //  如果找不到，或者找到的方法是抽象的，则抛出AbstractMethodError异常
        if (null == methodToBeInvoked || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError();
        }
        //  如果找到的方法不是public，则抛出IllegalAccessError异常
        if (!methodToBeInvoked.isPublic()) {
            throw new IllegalAccessError();
        }

        //  一切正常，调用方法
        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }
}
