package cn.zhaooo.jvm.tools;

import cn.zhaooo.jvm.classfile.ClassFile;
import cn.zhaooo.jvm.classfile.MemberInfo;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;
import cn.zhaooo.jvm.classfile.constantInfo.impl.*;
import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.rdta.heap.methodarea.Method;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;
import cn.zhaooo.jvm.rdta.heap.methodarea.StringPool;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
import cn.zhaooo.jvm.rdta.jvmstack.Slot;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @ClassName: LogTool
 * @Description:
 * @Author: zhaooo
 * @Date: 2024/01/07 15:57
 */
public class LogTool {
    // hack
    // 打印只支持基本类型和String
    public static void hackPrint(OperandStack stack, String descriptor, Consumer<java.lang.Object> function) {
        switch (descriptor) {
            case "(Z)V":
                function.accept(stack.popInt() != 0);
                break;
            case "(C)V":
            case "(I)V":
            case "(B)V":
            case "(S)V":
                function.accept(stack.popInt());
                break;
            case "(F)V":
                function.accept(stack.popFloat());
                break;
            case "(J)V":
                function.accept(stack.popLong());
                break;
            case "(D)V":
                function.accept(stack.popDouble());
                break;
            case "(Ljava/lang/String;)V":
                Object jStr = stack.popRef();
                String goStr = StringPool.goString(jStr);
                function.accept(goStr);
                break;
            default:
                function.accept(descriptor);
                break;
        }
        // 出栈this引用
        stack.popRef();
    }

    public static void logInstruction(Instruction instruction, Frame stackFrame) {
        int depth = stackFrame.getThread().getStack().getSize();
        Method curMethod = stackFrame.getMethod();
        String thisClassName = curMethod.getClazz().getName();
        String methodName = curMethod.getName();
        String instructionName = instruction.getClass().getSimpleName();
        int pc = stackFrame.getThread().getPC();
        OperandStack operandStack = stackFrame.getOperandStack();
        LocalVars localVars = stackFrame.getLocalVars();
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  ");
        }
        System.out.println(indent + thisClassName + "." + methodName + "() #" + pc + " -> " + instructionName);
        printOperandStack(operandStack, indent.toString());
        printLocalVars(localVars, indent.toString());
        System.out.println();
    }

    private static void printOperandStack(OperandStack operandStack, String indent) {
        System.out.println(indent + "=====OperandStack=====");
        int top = operandStack.getTop();
        for (int i = 0; i < operandStack.getSlots().length; i++) {
            Slot slot = operandStack.getSlots()[i];
            if (slot.ref != null) {
                System.out.println(indent + slot.ref.getClazz().getName() + (i == top ? " <--" : ""));
            } else {
                System.out.println(indent + slot.val + (i == top ? " <--" : ""));
            }
        }
        if (top == operandStack.getSlots().length) {
            System.out.println(indent + " <--");
        }
    }

    private static void printLocalVars(LocalVars localVars, String indent) {
        System.out.println(indent + "=======LocalVars======");
        for (Slot slot : localVars.getSlots()) {
            if (slot.ref != null) {
                System.out.println(indent + slot.ref.getClazz().getName());
            } else {
                System.out.println(indent + slot.val);
            }
        }
    }

    public static void printClassInfo(ClassFile cf) {
        System.out.println("version: " + cf.getMajorVersion() + "." + cf.getMinorVersion());
        System.out.println("constants count：" + cf.getConstantPool().getSize());
        System.out.format("access flags：0x%x\n", cf.getAccessFlags());
        System.out.println("this class：" + cf.getClassName());
        System.out.println("super class：" + cf.getSuperClassName());
        System.out.println("interfaces：" + Arrays.toString(cf.getInterfaceNames()));
        System.out.println("fields count：" + cf.getFields().length);
        for (MemberInfo memberInfo : cf.getFields()) {
            System.out.format("%s\t\t%s\n", memberInfo.getName(), memberInfo.getDescriptor());
        }
        System.out.println("methods count: " + cf.getMethods().length);
        for (MemberInfo memberInfo : cf.getMethods()) {
            System.out.format("%s\t\t%s\n", memberInfo.getName(), memberInfo.getDescriptor());
        }
        System.out.println("constant pool:");
        // 从1开始,0是无效索引，表示不指向任何常量
        for (int i = 1; i < cf.getConstantPool().getSize(); i++) {
            ConstantInfo info = cf.getConstantPool().getConstantInfos()[i];
            printConstantInfo(i, info);
        }

    }

    static void printConstantInfo(int index, ConstantInfo info) {
        switch (info.getTag()) {
            case ConstantInfo.CONSTANT_CLASS:
                ConstantClassInfo classInfo = (ConstantClassInfo) info;
                System.out.println("#" + index + " = CONSTANT_CLASS\t\t#" + classInfo.getNameIdx());
                break;
            case ConstantInfo.CONSTANT_FIELDREF:
                ConstantFieldrefInfo fieldrefInfo = (ConstantFieldrefInfo) info;
                System.out.println("#" + index + " = CONSTANT_FIELDREF\t\t#" + fieldrefInfo.getClassIdx() + ".#" + fieldrefInfo.getNameAndTypeIdx());
                break;
            case ConstantInfo.CONSTANT_METHODREF:
                ConstantMethodrefInfo methodrefInfo = (ConstantMethodrefInfo) info;
                System.out.println("#" + index + " = CONSTANT_METHODREF\t\t#" + methodrefInfo.getClassIdx() + ".#" + methodrefInfo.getNameAndTypeIdx());
                break;
            case ConstantInfo.CONSTANT_INTERFACE_METHODREF:
                ConstantInterfaceMethodrefInfo interfaceMethodrefInfo = (ConstantInterfaceMethodrefInfo) info;
                System.out.println("#" + index + " = CONSTANT_INTERFACE_METHODREF\t\t#" + interfaceMethodrefInfo.getClassIdx() + ".#" + interfaceMethodrefInfo.getNameAndTypeIdx());
                break;
            case ConstantInfo.CONSTANT_STRING:
                ConstantStringInfo stringInfo = (ConstantStringInfo) info;
                System.out.println("#" + index + " = CONSTANT_STRING\t\t#" + stringInfo.getStrIdx());
                break;
            case ConstantInfo.CONSTANT_INTEGER:
                ConstantIntegerInfo integerInfo = (ConstantIntegerInfo) info;
                System.out.println("#" + index + " = CONSTANT_INTEGER\t\t" + integerInfo.getVal());
                break;
            case ConstantInfo.CONSTANT_FLOAT:
                ConstantFloatInfo floatInfo = (ConstantFloatInfo) info;
                System.out.println("#" + index + " = CONSTANT_FLOAT\t\t" + floatInfo.getVal());
                break;
            case ConstantInfo.CONSTANT_LONG:
                ConstantLongInfo longInfo = (ConstantLongInfo) info;
                System.out.println("#" + index + " = CONSTANT_LONG\t\t" + longInfo.getVal());
                break;
            case ConstantInfo.CONSTANT_DOUBLE:
                ConstantDoubleInfo doubleInfo = (ConstantDoubleInfo) info;
                System.out.println("#" + index + " = CONSTANT_DOUBLE\t\t" + doubleInfo.getVal());
                break;
            case ConstantInfo.CONSTANT_NAME_AND_TYPE:
                ConstantNameAndTypeInfo nameAndTypeInfo = (ConstantNameAndTypeInfo) info;
                System.out.println("#" + index + " = CONSTANT_NAME_AND_TYPE\t\t#" + nameAndTypeInfo.getNameIdx() + ".#" + nameAndTypeInfo.getDescIdx());
                break;
            case ConstantInfo.CONSTANT_UTF8:
                ConstantUtf8Info utf8Info = (ConstantUtf8Info) info;
                System.out.println("#" + index + " = CONSTANT_UTF8\t\t" + utf8Info.getStr());
                break;
            case ConstantInfo.CONSTANT_METHOD_HANDLE:
                ConstantMethodHandleInfo methodHandleInfo = (ConstantMethodHandleInfo) info;
                System.out.println("#" + index + " = CONSTANT_METHOD_HANDLE\t\t" + methodHandleInfo.getReferenceKind() + ".#" + methodHandleInfo.getReferenceIdx());
                break;
            case ConstantInfo.CONSTANT_METHOD_TYPE:
                ConstantMethodTypeInfo methodTypeInfo = (ConstantMethodTypeInfo) info;
                System.out.println("#" + index + " = CONSTANT_METHOD_TYPE\t\t#" + methodTypeInfo.getDescriptorIdx());
                break;
            case ConstantInfo.CONSTANT_INVOKE_DYNAMIC:
                ConstantInvokeDynamicInfo invokeDynamicInfo = (ConstantInvokeDynamicInfo) info;
                System.out.println("#" + index + " = CONSTANT_INVOKE_DYNAMIC\t\t#" + invokeDynamicInfo.getBootstrapMethodAttrIdx() + ".#" + invokeDynamicInfo.getNameAndTypeIdx());
                break;
            default:
                System.out.println("Unknown ConstantInfo");

        }
    }

    // TODO 方法字节码显示

    public static String byteToHexString(byte[] codes) {
        StringBuilder sb = new StringBuilder("0x");
        for (byte b : codes) {
            sb.append(String.format("%02X", b & 0xFF));
        }
        return sb.toString();
    }
}
