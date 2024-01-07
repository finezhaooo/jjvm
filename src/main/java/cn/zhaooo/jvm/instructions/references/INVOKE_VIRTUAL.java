package cn.zhaooo.jvm.instructions.references;

import cn.zhaooo.jvm.instructions.base.MethodInvokeLogic;
import cn.zhaooo.jvm.instructions.base.impl.Index16Instruction;
import cn.zhaooo.jvm.rdta.heap.constantpool.MethodRef;
import cn.zhaooo.jvm.rdta.heap.constantpool.RunTimeConstantPool;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Method;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;
import cn.zhaooo.jvm.rdta.heap.methodarea.StringPool;
import cn.zhaooo.jvm.rdta.heap.util.MethodLookup;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
import cn.zhaooo.jvm.tools.LogTool;

import java.util.function.Consumer;

/**
 * 调用对象实例方法
 */
public class INVOKE_VIRTUAL extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        // 调用者
        Class currentClass = frame.getMethod().getClazz();
        RunTimeConstantPool runTimeConstantPool = currentClass.getRunTimeConstantPool();
        MethodRef methodRef = (MethodRef) runTimeConstantPool.getConstants(idx);

        Method resolvedMethod = methodRef.resolvedMethodRef();
        if (resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError();
        }

        // 从操作数栈中获得this引用
        Object ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (null == ref) {
            // 因为System类是一个特殊的类，它没有构造方法，也不能被实例化，所以它的实例引用总是null。
            // 但是它有一些静态字段，比如out，它是一个PrintStream类型的对象。
            // 所以当我们调用System.out.println时，我们实际上是调用了PrintStream类的println方法，这个方法是一个实例方法，所以需要用invokevirtual指令来调用。
            if ("println".equals(methodRef.getName())) {
                LogTool.hackPrint(frame.getOperandStack(), methodRef.getDescriptor(), System.out::println);
                return;
            }
            if ("print".equals(methodRef.getName())) {
                LogTool.hackPrint(frame.getOperandStack(), methodRef.getDescriptor(), System.out::print);
                return;
            }
            throw new NullPointerException();
        }

        // 如果resolvedMethod是protected的，那么它只能被它所在的类或者子类访问，或者在同一个包中的其他类访问。
        if (resolvedMethod.isProtected() &&
                // 如果resolvedMethod所在的类是currentClass的子类，那么说明currentClass是调用者，它想要访问子类的protected方法。这是不允许的，因为这样会破坏子类的封装性。
                resolvedMethod.getClazz().isSubClassOf(currentClass) &&
                // 如果resolvedMethod所在的类和currentClass不在同一个包中，那么说明currentClass是一个外部类，它想要访问另一个包中的protected方法。这也是不允许的，因为protected方法只能在同一个包中被访问。
                !resolvedMethod.getClazz().getPackageName().equals(currentClass.getPackageName()) &&
                // 如果ref所指向的对象的类不是currentClass，那么说明currentClass想要通过ref来间接访问resolvedMethod。这也是不允许的，因为protected方法只能通过this来访问。
                ref.getClazz() != currentClass &&
                // 如果ref所指向的对象的类不是currentClass的子类，那么说明currentClass想要通过ref来访问一个和它没有继承关系的类的protected方法。这也是不允许的，因为protected方法只能被它所在的类或者子类访问。
                !ref.getClazz().isSubClassOf(currentClass)) {
            throw new IllegalAccessError();
        }

        //  从对象的类中查找真正要调用的方法
        Method methodToBeInvoked = MethodLookup.lookupMethodInClass(ref.getClazz(), methodRef.getName(), methodRef.getDescriptor());
        //  如果找不到方法，或者找到的是抽象方法，则需要抛出AbstractMethodError异常
        if (null == methodToBeInvoked || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError();
        }

        //  一切正常，调用方法
        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }
}
