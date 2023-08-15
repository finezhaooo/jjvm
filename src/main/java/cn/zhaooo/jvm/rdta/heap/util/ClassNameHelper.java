package cn.zhaooo.jvm.rdta.heap.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ClassNameHelper
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/08/07 23:31
 */
public class ClassNameHelper {
    static Map<String, String> primitiveTypes = new HashMap<String, String>() {
        {
            put("void", "V");
            put("boolean", "Z");
            put("byte", "B");
            put("short", "S");
            put("int", "I");
            put("long", "J");
            put("char", "C");
            put("float", "F");
            put("double", "D");
        }
    };

    /**
     * 根据类名得到数组类名
     */
    public static String getArrayClassName(String className) {
        return "[" + toDescriptor(className);
    }

    /**
     * 根据数组类名推测出数组元素类名
     */
    public static String getComponentClassName(String className) {
        //  数组类名以方括号开头，将它去掉就是数组元素的类型描述符
        if (className.getBytes()[0] == '[') {
            String componentTypeDescriptor = className.substring(1);
            return toClassName(componentTypeDescriptor);
        }
        throw new RuntimeException("Not array " + className);
    }

    /**
     * 将类名变成类型描述符
     */
    private static String toDescriptor(String className) {
        //  如果是数组类名，描述符就是类名
        if (className.getBytes()[0] == '[') {
            return className;
        }

        //  如果是基本类型名，返回对应的类型描述符
        String d = primitiveTypes.get(className);
        if (null != d) return d;

        return "L" + className + ";";
    }

    /**
     * 将类型描述符转换成类名
     */
    private static String toClassName(String descriptor) {
        byte descByte = descriptor.getBytes()[0];

        //  如果类型描述符以 '[' 开头，那么肯定是数组，描述符即是类名
        if (descByte == '[') {
            return descriptor;
        }

        //  如果类型描述符以 'L' 开头，那么肯定是类描述符，去掉开头的L和末尾的分号即是类名
        if (descByte == 'L') {
            return descriptor.substring(1, descriptor.length() - 1);
        }

        //  判断是否是基本类型的描述符，如果是，返回基本类型名称
        for (Map.Entry<String, String> entry : primitiveTypes.entrySet()) {
            if (entry.getValue().equals(descriptor)) {
                return entry.getKey();
            }
        }

        throw new RuntimeException("Invalid descriptor " + descriptor);
    }
}
