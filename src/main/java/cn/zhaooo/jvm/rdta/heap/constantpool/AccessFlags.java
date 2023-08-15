package cn.zhaooo.jvm.rdta.heap.constantpool;

/**
 * @ClassName: AccessFlags
 * @Description: 访问标志
 * @Author: zhaooo
 * @Date: 2023/08/07 16:47
 */
public class AccessFlags {
    public static int ACC_PUBLIC       = 0x0001; // 类 字段 方法
    public static int ACC_PRIVATE      = 0x0002; // 类 字段 方法
    public static int ACC_PROTECTED    = 0x0004; // 类 字段 方法
    public static int ACC_STATIC       = 0x0008; // 类 字段 方法
    public static int ACC_FINAL        = 0x0010; // 类 字段 方法
    public static int ACC_SUPER        = 0x0020; // 类
    public static int ACC_SYNCHRONIZED = 0x0020; //        方法
    public static int ACC_VOLATILE     = 0x0040; //    字段
    public static int ACC_BRIDGE       = 0x0040; //        方法
    public static int ACC_TRANSIENT    = 0x0080; //    字段
    public static int ACC_VARARGS      = 0x0080; //        方法
    public static int ACC_NATIVE       = 0x0100; //        方法
    public static int ACC_INTERFACE    = 0x0200; // 类
    public static int ACC_ABSTRACT     = 0x0400; // 类     方法
    public static int ACC_STRICT       = 0x0800; //        方法
    public static int ACC_SYNTHETIC    = 0x1000; // 类 字段 方法
    public static int ACC_ANNOTATION   = 0x2000; // 类
    public static int ACC_ENUM         = 0x4000; // 类 字段
}
