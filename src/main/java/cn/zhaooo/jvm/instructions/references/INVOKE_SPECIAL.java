package cn.zhaooo.jvm.instructions.references;

import cn.zhaooo.jvm.instructions.base.MethodInvokeLogic;
import cn.zhaooo.jvm.instructions.base.impl.Index16Instruction;
import cn.zhaooo.jvm.rdta.heap.constantpool.MethodRef;
import cn.zhaooo.jvm.rdta.heap.constantpool.RunTimeConstantPool;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Method;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;
import cn.zhaooo.jvm.rdta.heap.util.MethodLookup;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;

/**
 * 调用私有方法和初始化方法
 */
public class INVOKE_SPECIAL  extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        //  拿到当前类、当前常量池、方法符号引用
        Class currentClass = frame.getMethod().getClazz();
        RunTimeConstantPool runTimeConstantPool = currentClass.getConstantPool();
        MethodRef methodRef = (MethodRef) runTimeConstantPool.getConstants(idx);

        //  解析方法符号引用，拿到解析后的类和方法
        Class resolvedClass = methodRef.resolvedClass();
        Method resolvedMethod = methodRef.resolvedMethodRef();

        //  假定从方法符号引用中解析出来的类是c，方法是M
        if ("<init>".equals(resolvedMethod.getName()) && resolvedMethod.getClazz() != resolvedClass) {
            //  如果M是构造函数，则申明M的类必须是c，否则抛出NoSuchMethodError异常
            throw new NoSuchMethodError();
        }
        if (resolvedMethod.isStatic()) {
            //  如果M是静态方法，则抛出IncompatibleClassChangeError异常
            throw new IncompatibleClassChangeError();
        }

        //  从操作数栈中获得this引用
        Object ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (null == ref) {
            //  如果该引用时null，抛出NullPointerException异常
            throw new NullPointerException();
        }

        //  确保protected方法只能被声明该方法的类或子类调用
        if (resolvedMethod.isProtected() &&
                resolvedMethod.getClazz().isSubClassOf(currentClass) &&
                !resolvedMethod.getClazz().getPackageName().equals(currentClass.getPackageName()) &&
                ref.getClazz() != currentClass &&
                !ref.getClazz().isSubClassOf(currentClass)) {
            //  如果违反这一规定，则抛出IllegalAccessError异常
            throw new IllegalAccessError();
        }

        //  如果调用超类中的函数不是构造函数，且当前类的ACC_SUPER标志被设置，
        //  需要一个额外的过程查找最终要调用的方法,
        //  否则前面从方法符号引用中解析出来的方法就是要调用的方法
        Method methodToBeInvoked = resolvedMethod;
        if (currentClass.isSuper() &&
                resolvedClass.isSubClassOf(currentClass) &&
                !resolvedMethod.getName().equals("<init>")) {
            MethodLookup.lookupMethodInClass(currentClass.superClass, methodRef.getName(), methodRef.getDescriptor());
        }

        //  如果查找失败，或者找到的方法是抽象的，抛出AbstractMethodError异常
        if (methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError();
        }

        //  如果一切正常，就调用方法
        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }
}