package cn.zhaooo.jvm.rdta.heap.methodarea;

import java.util.HashMap;
import java.util.Map;

import cn.zhaooo.jvm.ClassLoader;

/**
 * @ClassName: StringPool
 * @Description: 字符串常量池
 * @Author: zhaooo
 * @Date: 2023/08/07 23:40
 */
public class StringPool {
    /**
     * 缓存当前虚拟机加载的所有字符串
     */
    private static Map<String, Object> internedStrs = new HashMap<>();

    public static Object jString(ClassLoader loader, String goStr) {
        Object internedStr = internedStrs.get(goStr);
        if (null != internedStr) return internedStr;

        char[] chars = goStr.toCharArray();
        // 底层是char数组  private final char value[];
        Object jChars = new Object(loader.loadClass("[C"), chars);
        Object jStr = loader.loadClass("java/lang/String").newObject();
        jStr.setRefVal("value", "[C", jChars);

        internedStrs.put(goStr, jStr);
        return jStr;
    }

    public static String goString(Object jStr) {
        Object charArr = jStr.getRefVar("value", "[C");
        return new String(charArr.getChars());
    }
}
