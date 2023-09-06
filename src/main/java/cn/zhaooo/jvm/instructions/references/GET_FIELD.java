package cn.zhaooo.jvm.instructions.references;

import cn.zhaooo.jvm.instructions.base.impl.Index16Instruction;
import cn.zhaooo.jvm.rdta.heap.constantpool.FieldRef;
import cn.zhaooo.jvm.rdta.heap.constantpool.RunTimeConstantPool;
import cn.zhaooo.jvm.rdta.heap.methodarea.Field;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;
import cn.zhaooo.jvm.rdta.heap.methodarea.Slots;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * get a field value of an object objectref, where the field is identified by field reference in the constant pool index (indexbyte1 << 8 | indexbyte2)
 * 获取对象的实例变量值，然后推入操作数栈  指令后是2字节索引，操作数栈顶是对象引用
 */
public class GET_FIELD extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        RunTimeConstantPool runTimeConstantPool = frame.getMethod().getClazz().getConstantPool();
        FieldRef fieldRef = (FieldRef) runTimeConstantPool.getConstants(idx);
        Field field = fieldRef.resolvedFieldRef();

        OperandStack stack = frame.getOperandStack();
        Object ref = stack.popRef();
        if (null == ref) {
            throw new NullPointerException();
        }
        // 从对象中获得类变量数据
        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        Slots slots = ref.getFields();

        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                stack.pushInt(slots.getInt(slotId));
                break;
            case 'F':
                stack.pushFloat(slots.getFloat(slotId));
                break;
            case 'J':
                stack.pushLong(slots.getLong(slotId));
                break;
            case 'D':
                stack.pushDouble(slots.getDouble(slotId));
                break;
            case 'L':
            case '[':
                stack.pushRef(slots.getRef(slotId));
                break;
            default:
                break;
        }
    }
}