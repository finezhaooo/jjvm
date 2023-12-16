import cn.zhaooo.jvm.classfile.ClassFile;
import cn.zhaooo.jvm.classfile.MemberInfo;
import cn.zhaooo.jvm.classfile.constantInfo.ConstantInfo;
import cn.zhaooo.jvm.classfile.constantInfo.impl.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @ClassName: ClassFileTest
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/12/16 14:55
 */
public class ClassFileTest {
    public static void main(String[] args) {
        File file = new File("C:\\Projects\\jjvm\\target\\test-classes\\helloTest.class"); // 替换为你实际的文件路径
        byte[] buffer = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            buffer = new byte[(int) file.length()];
            int bytesRead = fis.read(buffer);
            if (bytesRead != -1) {
                // 读取成功，可以在这里使用读取到的字节数组(buffer)
                // 注意：如果文件很大，可以分批读取，而不是一次性读取所有字节
                System.out.println("Read " + bytesRead + " bytes from the file.");
            } else {
                System.out.println("Failed to read from the file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClassFile classFile = new ClassFile(buffer);
        printClassInfo(classFile);
    }

    static void printClassInfo(ClassFile cf) {
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

    //TODO 方法字节码显示
}