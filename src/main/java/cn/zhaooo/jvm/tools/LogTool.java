package cn.zhaooo.jvm.tools;

import cn.zhaooo.jvm.classfile.ClassFile;
import cn.zhaooo.jvm.classfile.MemberInfo;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;
import cn.zhaooo.jvm.classfile.constantInfo.impl.*;
import cn.zhaooo.jvm.instructions.Factory;
import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.instructions.base.impl.Index16Instruction;
import cn.zhaooo.jvm.instructions.base.impl.Index8Instruction;
import cn.zhaooo.jvm.rdta.heap.constantpool.AccessFlags;
import cn.zhaooo.jvm.rdta.heap.methodarea.Method;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;
import cn.zhaooo.jvm.rdta.heap.methodarea.StringPool;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;
import cn.zhaooo.jvm.rdta.jvmstack.Slot;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @ClassName: LogTool
 * @Description:
 * @Author: zhaooo
 * @Date: 2024/01/07 15:57
 */
public class LogTool {
    // hack
    // 打印只支持基本类型和String
    public static void hackPrint(OperandStack stack, String descriptor, Consumer<java.lang.Object> sysOut, Function<java.lang.Object, java.lang.StringBuilder> strOut) {
        switch (descriptor) {
            case "(Z)V":
                boolean bVal = stack.popInt() != 0;
                if (sysOut != null) sysOut.accept(bVal);
                if (strOut != null) strOut.apply(bVal);
                break;
            case "(C)V":
            case "(I)V":
            case "(B)V":
            case "(S)V":
                int iVal = stack.popInt();
                if (sysOut != null) sysOut.accept(iVal);
                if (strOut != null) strOut.apply(iVal);
                break;
            case "(F)V":
                float fVal = stack.popFloat();
                if (sysOut != null) sysOut.accept(fVal);
                if (strOut != null) strOut.apply(fVal);
                break;
            case "(J)V":
                long lVal = stack.popLong();
                if (sysOut != null) sysOut.accept(lVal);
                if (strOut != null) strOut.apply(lVal);
                break;
            case "(D)V":
                double dVal = stack.popDouble();
                if (sysOut != null) sysOut.accept(dVal);
                if (strOut != null) strOut.apply(dVal);
                break;
            case "(Ljava/lang/String;)V":
                Object jStr = stack.popRef();
                String goStr = StringPool.goString(jStr);
                if (sysOut != null) sysOut.accept(goStr);
                if (strOut != null) strOut.apply(goStr);
                break;
            default:
                if (sysOut != null) sysOut.accept(descriptor);
                if (strOut != null) strOut.apply(descriptor);
                break;
        }
    }

    public static String logInstruction(Instruction instruction, Frame stackFrame) {
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
        return indent + thisClassName + "." + methodName + "() #" + pc + " -> " + instructionName + "\n" +
                printOperandStack(operandStack, indent.toString()) +
                printLocalVars(localVars, indent.toString()) +
                System.lineSeparator();
    }

    private static String printOperandStack(OperandStack operandStack, String indent) {
        StringBuilder opStack = new StringBuilder(indent + "=====OperandStack=====\n");
        int top = operandStack.getTop();
        for (int i = 0; i < operandStack.getSlots().length; i++) {
            Slot slot = operandStack.getSlots()[i];
            if (slot.ref != null) {
                opStack.append(indent).append(slot.ref.getClazz().getName()).append(i == top ? " <--" : "").append("\n");
            } else {
                opStack.append(indent).append(slot.val).append(i == top ? " <--" : "").append("\n");
            }
        }
        if (top == operandStack.getSlots().length) {
            opStack.append(indent).append(" <--").append("\n");
        }
        return opStack.toString();
    }

    private static String printLocalVars(LocalVars localVars, String indent) {
        StringBuilder lacVars = new StringBuilder(indent + "=======LocalVars======\n");
        for (Slot slot : localVars.getSlots()) {
            if (slot.ref != null) {
                lacVars.append(indent).append(slot.ref.getClazz().getName()).append("\n");
            } else {
                lacVars.append(indent).append(slot.val).append("\n");
            }
        }
        return lacVars.toString();
    }

    public static String printClassInfo(ClassFile cf) {
        StringBuilder classInfo = new StringBuilder();
        classInfo.append("version: ").append(cf.getMajorVersion()).append(".").append(cf.getMinorVersion()).append("\n");
        classInfo.append("constants count: ").append(cf.getConstantPool().getSize()).append("\n");
        classInfo.append("access flags: ").append(AccessFlags.getName(cf.getAccessFlags())).append("\n");
        classInfo.append("this class: ").append(cf.getClassName()).append("\n");
        classInfo.append("super class: ").append(cf.getSuperClassName()).append("\n");
        classInfo.append("interfaces: ").append(Arrays.toString(cf.getInterfaceNames())).append("\n");
        classInfo.append("fields count: ").append(cf.getFields().length).append("\n");
        classInfo.append("methods count: ").append(cf.getMethods().length).append("\n");
        classInfo.append("constant pool:").append("\n");
        // 从1开始,0是无效索引，表示不指向任何常量
        for (int i = 1; i < cf.getConstantPool().getSize(); i++) {
            ConstantInfo info = cf.getConstantPool().getConstantInfos()[i];
            classInfo.append("  ").append(printConstantInfo(i, info)).append("\n");
        }
        classInfo.append("\n");
        for (MemberInfo memberInfo : cf.getFields()) {
            classInfo.append(printFiledInfo(memberInfo)).append("\n");
        }
        for (MemberInfo memberInfo : cf.getMethods()) {
            classInfo.append(printMethodInfo(memberInfo)).append("\n");
        }
        return classInfo.toString();

    }

    static String printFiledInfo(MemberInfo fieldInfo){
        return "access flags: " + AccessFlags.getName(fieldInfo.getAccessFlags()) + "\n" +
                "name: " + fieldInfo.getName() + "\n" +
                "descriptor: " + fieldInfo.getDescriptor() + "\n";
    }
    static String printMethodInfo(MemberInfo methodInfo) {
        StringBuilder builder = new StringBuilder();
        builder.append("access flags: ").append(AccessFlags.getName(methodInfo.getAccessFlags())).append("\n");
        builder.append("name: ").append(methodInfo.getName()).append("\n");
        builder.append("descriptor: ").append(methodInfo.getDescriptor()).append("\n");
        builder.append("code: ").append("\n");
        builder.append("  ").append("stack=").append(methodInfo.getCodeAttribute().getMaxStack())
                .append(", locals=").append(methodInfo.getCodeAttribute().getMaxLocals())
                .append("\n");
        byte[] code = methodInfo.getCodeAttribute().getData();
        for (int i = 0; i < code.length; i++) {
            Instruction instruction = Factory.create(code[i]);
            if (instruction == null) {
                builder.append("  ").append(i).append(": ").append("Unsupported opcode ").append(LogTool.byteToHexString(new byte[]{code[i]})).append("\n");
            } else if (instruction instanceof Index8Instruction) {
                int index = code[++i] & 0xFF;
                builder.append("  ").append(i).append(": ").append(instruction.getClass().getSimpleName()).append(" #").append(index).append("\n");
            } else if (instruction instanceof Index16Instruction) {
                int byte1 = code[++i] & 0xFF;
                int byte2 = code[++i] & 0xFF;
                int index = (byte1 << 8) | byte2;
                builder.append("  ").append(i).append(": ").append(instruction.getClass().getSimpleName()).append(" #").append(index).append("\n");
            } else {
                builder.append("  ").append(i).append(": ").append(instruction.getClass().getSimpleName()).append("\n");
            }
        }
        return builder.toString();
    }

    static String printConstantInfo(int index, ConstantInfo info) {
        switch (info.getTag()) {
            case ConstantInfo.CONSTANT_CLASS:
                ConstantClassInfo classInfo = (ConstantClassInfo) info;
                return ("#" + index + " = CONSTANT_CLASS\t\t#" + classInfo.getNameIdx());

            case ConstantInfo.CONSTANT_FIELDREF:
                ConstantFieldrefInfo fieldrefInfo = (ConstantFieldrefInfo) info;
                return ("#" + index + " = CONSTANT_FIELDREF\t\t#" + fieldrefInfo.getClassIdx() + ".#" + fieldrefInfo.getNameAndTypeIdx());

            case ConstantInfo.CONSTANT_METHODREF:
                ConstantMethodrefInfo methodrefInfo = (ConstantMethodrefInfo) info;
                return ("#" + index + " = CONSTANT_METHODREF\t\t#" + methodrefInfo.getClassIdx() + ".#" + methodrefInfo.getNameAndTypeIdx());

            case ConstantInfo.CONSTANT_INTERFACE_METHODREF:
                ConstantInterfaceMethodrefInfo interfaceMethodrefInfo = (ConstantInterfaceMethodrefInfo) info;
                return ("#" + index + " = CONSTANT_INTERFACE_METHODREF\t\t#" + interfaceMethodrefInfo.getClassIdx() + ".#" + interfaceMethodrefInfo.getNameAndTypeIdx());

            case ConstantInfo.CONSTANT_STRING:
                ConstantStringInfo stringInfo = (ConstantStringInfo) info;
                return ("#" + index + " = CONSTANT_STRING\t\t#" + stringInfo.getStrIdx());

            case ConstantInfo.CONSTANT_INTEGER:
                ConstantIntegerInfo integerInfo = (ConstantIntegerInfo) info;
                return ("#" + index + " = CONSTANT_INTEGER\t\t" + integerInfo.getVal());

            case ConstantInfo.CONSTANT_FLOAT:
                ConstantFloatInfo floatInfo = (ConstantFloatInfo) info;
                return ("#" + index + " = CONSTANT_FLOAT\t\t" + floatInfo.getVal());

            case ConstantInfo.CONSTANT_LONG:
                ConstantLongInfo longInfo = (ConstantLongInfo) info;
                return ("#" + index + " = CONSTANT_LONG\t\t" + longInfo.getVal());

            case ConstantInfo.CONSTANT_DOUBLE:
                ConstantDoubleInfo doubleInfo = (ConstantDoubleInfo) info;
                return ("#" + index + " = CONSTANT_DOUBLE\t\t" + doubleInfo.getVal());

            case ConstantInfo.CONSTANT_NAME_AND_TYPE:
                ConstantNameAndTypeInfo nameAndTypeInfo = (ConstantNameAndTypeInfo) info;
                return ("#" + index + " = CONSTANT_NAME_AND_TYPE\t\t#" + nameAndTypeInfo.getNameIdx() + ".#" + nameAndTypeInfo.getDescIdx());

            case ConstantInfo.CONSTANT_UTF8:
                ConstantUtf8Info utf8Info = (ConstantUtf8Info) info;
                return ("#" + index + " = CONSTANT_UTF8\t\t" + utf8Info.getStr());

            case ConstantInfo.CONSTANT_METHOD_HANDLE:
                ConstantMethodHandleInfo methodHandleInfo = (ConstantMethodHandleInfo) info;
                return ("#" + index + " = CONSTANT_METHOD_HANDLE\t\t" + methodHandleInfo.getReferenceKind() + ".#" + methodHandleInfo.getReferenceIdx());

            case ConstantInfo.CONSTANT_METHOD_TYPE:
                ConstantMethodTypeInfo methodTypeInfo = (ConstantMethodTypeInfo) info;
                return ("#" + index + " = CONSTANT_METHOD_TYPE\t\t#" + methodTypeInfo.getDescriptorIdx());

            case ConstantInfo.CONSTANT_INVOKE_DYNAMIC:
                ConstantInvokeDynamicInfo invokeDynamicInfo = (ConstantInvokeDynamicInfo) info;
                return ("#" + index + " = CONSTANT_INVOKE_DYNAMIC\t\t#" + invokeDynamicInfo.getBootstrapMethodAttrIdx() + ".#" + invokeDynamicInfo.getNameAndTypeIdx());
            default:
                return ("Unknown ConstantInfo");

        }
    }

    public static String byteToHexString(byte[] codes) {
        StringBuilder sb = new StringBuilder("0x");
        for (byte b : codes) {
            sb.append(String.format("%02X", b & 0xFF));
        }
        return sb.toString();
    }
}
