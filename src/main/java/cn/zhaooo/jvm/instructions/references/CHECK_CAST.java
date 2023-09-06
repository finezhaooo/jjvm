package cn.zhaooo.jvm.instructions.references;

import cn.zhaooo.jvm.instructions.base.impl.Index16Instruction;
import cn.zhaooo.jvm.rdta.heap.constantpool.ClassRef;
import cn.zhaooo.jvm.rdta.heap.constantpool.RunTimeConstantPool;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
/**
 * checks whether an objectref is of a certain type, the class reference of which is in the constant pool at index
 * checkcast指令和instanceof指令很像，区别在于：
 * instanceof指令会改变操作数栈（弹出对象引用，推入判断结果），
 * checkcast则不改变操作数栈（如果判断失败，直接抛出ClassCastException异常）
 */
public class CHECK_CAST extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        //  从操作数栈中弹出对象引用，再推回去
        OperandStack stack = frame.getOperandStack();
        Object ref = stack.popRef();
        stack.pushRef(ref);

        //  如果引用是null，则指令执行结束，null引用可以转换成任何类型
        if (null == ref) {
            return;
        }
        //  否则解析类符号引用，判断对象是否是类的实例，
        //  如果是的话，指令执行结束
        RunTimeConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        ClassRef clazzRef = (ClassRef) cp.getConstants(idx);
        Class clazz = clazzRef.resolvedClass();
        // isInstanceOf->isAssignableFrom->isSubClassOf/isImplements
        if (!ref.isInstanceOf(clazz)){
            throw new ClassCastException(ref.getClass().getName() + "can not be cast to" + clazz.getName());
        }
    }
}
