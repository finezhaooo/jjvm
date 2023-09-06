package cn.zhaooo.jvm.instructions.references;

import cn.zhaooo.jvm.instructions.base.ClassInitLogic;
import cn.zhaooo.jvm.instructions.base.MethodInvokeLogic;
import cn.zhaooo.jvm.instructions.base.impl.Index16Instruction;
import cn.zhaooo.jvm.rdta.heap.constantpool.MethodRef;
import cn.zhaooo.jvm.rdta.heap.constantpool.RunTimeConstantPool;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Method;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;

/**
 * 调用静态方法
 */
public class INVOKE_STATIC extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        //  解析方法符号引用
        RunTimeConstantPool runTimeConstantPool = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = (MethodRef) runTimeConstantPool.getConstants(idx);
        Method resolvedMethod = methodRef.resolvedMethodRef();

        if (!resolvedMethod.isStatic()) {
            //  解析得到的方法不是静态方法，抛出IncompatibleClassChangeError异常
            throw new IncompatibleClassChangeError();
        }

        //  类初始方法只能由Java虚拟机调用，不能使用invokestatic指令调用
        Class clazz = resolvedMethod.getClazz();
        if (!clazz.getInitStarted()) {
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.getThread(), clazz);
            return;
        }

        MethodInvokeLogic.invokeMethod(frame, resolvedMethod);
    }
}
