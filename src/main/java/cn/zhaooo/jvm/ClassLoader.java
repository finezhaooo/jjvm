package cn.zhaooo.jvm;

import cn.zhaooo.jvm.classfile.ClassFile;
import cn.zhaooo.jvm.classpath.Classpath;
import cn.zhaooo.jvm.rdta.heap.constantpool.AccessFlags;
import cn.zhaooo.jvm.rdta.heap.constantpool.RunTimeConstantPool;
import cn.zhaooo.jvm.rdta.heap.methodarea.*;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ClassLoader
 * @Description: 类加载器 Java天生可以动态扩展的语言特性就是依赖运行期动态加载和动态连接这个特点实现的
 * @Author: zhaooo
 * @Date: 2023/08/07 23:37
 */
public class ClassLoader {

    // 类路径
    private Classpath classpath;
    // 已经加载的类数据，key是类的完全限定名，方法区的具体实现
    // 不同的类加载器即使加载同一个类名也是不同的类即
    private Map<String, Class> classMap;

    public ClassLoader(Classpath classpath) {
        this.classpath = classpath;
        classMap = new HashMap<>();
    }

    /**
     * 把类数据加载到方法区
     * 1）通过一个类的全限定名来获取定义此类的二进制字节流。
     * 2）将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构。
     * 3）在内存中生成一个代表这个类的java.lang.Class对象，作为方法区这个类的各种数据的访问入口。
     * <p>
     * 它并没有指明二进制字节流必须得从某个Class文件中获取，确切地说是根本没有指明要从哪里获取、如何获取
     */
    public Class loadClass(String className) {
        Class clazz = classMap.get(className);
        //  查找classMap，看类是否已经被加载
        if (null != clazz) {
            //  如果是，直接返回类数据
            return clazz;
        }
        //  要加载的类是数组类
        if (className.getBytes()[0] == '[') {
            return loadArrayClass(className);
        }
        try {
            return loadNonArrayClass(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载数组类
     * <p>
     * 实际上数组类本身不通过类加载器创建，它是由Java虚拟机直接在内存中动态构造出来的
     *
     * @param className 数组类的名称，然后根据名称中的方括号个数来确定数组的维度，以及根据名称中的元素类型来确定数组的组件类型。
     * @return
     */
    private Class loadArrayClass(String className) {
        Class clazz = new Class(AccessFlags.ACC_PUBLIC,
                className,
                this,
                true,
                this.loadClass("java/lang/Object"),
                new Class[]{this.loadClass("java/lang/Cloneable"), this.loadClass("java/io/Serializable")});
        classMap.put(className, clazz);
        return clazz;
    }

    /**
     * 加载普通类
     */
    private Class loadNonArrayClass(String className) throws Exception {
        //  找到class文件并把数据读取到内存
        byte[] data = classpath.readClass(className);
        if (null == data) {
            throw new ClassNotFoundException(className);
        }

        //  解析class文件，生成虚拟机可以使用的类数据，并放入方法区
        Class clazz = defineClass(data);

        //  链接
        link(clazz);
        return clazz;
    }

    /**
     * 将byte[]转化为java类
     */
    private Class defineClass(byte[] data) throws Exception {
        Class clazz = parseClass(data);
        clazz.loader = this;
        resolveSuperClass(clazz);
        resolveInterfaces(clazz);
        classMap.put(clazz.name, clazz);
        return clazz;
    }

    /**
     * 将class文件数据转换成class结构体
     */
    private Class parseClass(byte[] data) {
        ClassFile classFile = new ClassFile(data);
        return new Class(classFile);
    }

    /**
     * 解析超类
     */
    private void resolveSuperClass(Class clazz) throws Exception {
        // Object没有父类
        if (!clazz.name.equals("java/lang/Object")) {
            clazz.superClass = clazz.loader.loadClass(clazz.superClassName);
        }
    }

    /**
     * 解析接口
     */
    private void resolveInterfaces(Class clazz) throws Exception {
        int interfaceCount = clazz.interfaceNames.length;
        if (interfaceCount > 0) {
            clazz.interfaces = new Class[interfaceCount];
            for (int i = 0; i < interfaceCount; i++) {
                clazz.interfaces[i] = clazz.loader.loadClass(clazz.interfaceNames[i]);
            }
        }
    }

    /**
     * 链接过程：验证（Verification）、准备（Preparation）、解析（Resolution）
     */
    private void link(Class clazz) {
        verify(clazz);
        prepare(clazz);
    }

    /**
     * 验证阶段
     * 验证阶段大致上会完成下面四个阶段的检验动作：文件格式验证、元数据验证、字节码验证和符号引用验证。
     */
    private void verify(Class clazz) {
        // todo
    }

    /**
     * 准备阶段
     * 准备阶段是正式为类中定义的变量（即静态变量，被static修饰的变量）分配内存并设置类变量初始值的阶段
     */
    private void prepare(Class clazz) {
        calcInstanceFieldSlotIds(clazz);
        calcStaticFieldSlotIds(clazz);
        allocAndInitStaticVars(clazz);
    }

    /**
     * 计算实例字段的个数，同时编号
     */
    private void calcInstanceFieldSlotIds(Class clazz) {
        int slotId = 0;
        // 父类实例字段
        if (clazz.superClass != null) {
            slotId = clazz.superClass.instanceSlotCount;
        }
        for (Field field : clazz.fields) {
            if (!field.isStatic()) {
                field.slotId = slotId;
                slotId++;
                // 占2个槽
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        // 当前类以及其父类的实例字段的个数
        clazz.instanceSlotCount = slotId;
    }

    /**
     * 计算静态字段的个数，同时编号
     */
    private void calcStaticFieldSlotIds(Class clazz) {
        int slotId = 0;
        for (Field field : clazz.fields) {
            if (field.isStatic()) {
                field.slotId = slotId;
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.staticSlotCount = slotId;
    }

    /**
     * 给类变量分配空间
     */
    private void allocAndInitStaticVars(Class clazz) {
        clazz.staticVars = new Slots(clazz.staticSlotCount);
        for (Field field : clazz.fields) {
            if (field.isStatic() && field.isFinal()) {
                initStaticFinalVar(clazz, field);
            }
        }
    }

    /**
     * 给类变量赋予初始值
     */
    private void initStaticFinalVar(Class clazz, Field field) {
        Slots staticVars = clazz.staticVars;
        RunTimeConstantPool constantPool = clazz.runTimeConstantPool;
        int cpIdx = field.getConstValueIndex();
        int slotId = field.getSlotId();
        if (cpIdx > 0) {
            switch (field.getDescriptor()) {
                // 都用int表示
                case "Z":
                case "B":
                case "C":
                case "S":
                case "I":
                    staticVars.setInt(slotId, (Integer) constantPool.getConstants(cpIdx));
                    break;
                case "J":
                    staticVars.setLong(slotId, (Long) constantPool.getConstants(cpIdx));
                    break;
                case "F":
                    staticVars.setFloat(slotId, (Float) constantPool.getConstants(cpIdx));
                    break;
                case "D":
                    staticVars.setDouble(slotId, (Double) constantPool.getConstants(cpIdx));
                    break;
                case "Ljava/lang/String;":
                    String goStr = (String) constantPool.getConstants(cpIdx);
                    // 静态类变量，保存到StringPool
                    Object jStr = StringPool.jString(clazz.getLoader(), goStr);
                    staticVars.setRef(slotId, jStr);
                    break;
                default:
                    throw new ClassFormatError("Not supported type: " + field.getDescriptor());
            }
        }
    }
}