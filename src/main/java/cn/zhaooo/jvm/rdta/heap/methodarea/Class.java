package cn.zhaooo.jvm.rdta.heap.methodarea;

import cn.zhaooo.jvm.ClassLoader;
import cn.zhaooo.jvm.classfile.ClassFile;
import cn.zhaooo.jvm.rdta.heap.constantpool.AccessFlags;
import cn.zhaooo.jvm.rdta.heap.constantpool.RunTimeConstantPool;
import cn.zhaooo.jvm.rdta.heap.util.ClassNameHelper;

/**
 * @ClassName: Class
 * @Description: 类
 * @Author: zhaooo
 * @Date: 2023/08/07 16:45
 */
public class Class {

    private final int accessFlags;                             //  类的访问标志，总共16比特
    private String name;                                 //  类名
    private String superClassName;                       //  超类名
    private String[] interfaceNames;                     //  接口名
    private RunTimeConstantPool runTimeConstantPool;     //  运行时常量池
    private Field[] fields;                              //  字段表
    private Method[] methods;                            //  方法表
    private ClassLoader loader;                          //  类加载器
    private Class superClass;                            //  超类
    private Class[] interfaces;                          //  接口
    private int instanceSlotCount;                       //  实例变量占据的空间大小
    private int staticSlotCount;                         //  类变量占据的空间大小
    private Slots staticVars;                            //  静态变量
    private boolean initStarted;                         //  类是否初始化

    /**
     * 基于加载的类文件构造class
     */
    public Class(ClassFile classFile) {
        accessFlags = classFile.getAccessFlags();
        name = classFile.getClassName();
        superClassName = classFile.getSuperClassName();
        interfaceNames = classFile.getInterfaceNames();
        runTimeConstantPool = new RunTimeConstantPool(this, classFile.getConstantPool());
        fields = new Field().create(this, classFile.getFields());
        methods = new Method().create(this, classFile.getMethods());
    }

    /**
     * 数组类构造函数y
     */
    public Class(int accessFlags, String name, ClassLoader loader, boolean initStarted, Class superClass, Class[] interfaces) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.loader = loader;
        this.initStarted = initStarted;
        this.superClass = superClass;
        this.interfaces = interfaces;
    }

    /**
     * 创建普通对象
     */
    public Object newObject() {
        return new Object(this);
    }

    /**
     * 创建数组对象
     */
    public Object newArray(int count) {
        if (!isArray()) {
            throw new RuntimeException("Not array class " + name);
        }
        switch (getName()) {
            case "[Z":
                return new Object(this, new byte[count]);
            case "[B":
                return new Object(this, new byte[count]);
            case "[C":
                return new Object(this, new char[count]);
            case "[S":
                return new Object(this, new short[count]);
            case "[I":
                return new Object(this, new int[count]);
            case "[J":
                return new Object(this, new long[count]);
            case "[F":
                return new Object(this, new float[count]);
            case "[D":
                return new Object(this, new double[count]);
            default:
                return new Object(this, new Object[count]);
        }
    }


    public boolean isPublic() {
        return 0 != (accessFlags & AccessFlags.ACC_PUBLIC);
    }

    public boolean isFinal() {
        return 0 != (accessFlags & AccessFlags.ACC_FINAL);
    }

    public boolean isSuper() {
        return 0 != (accessFlags & AccessFlags.ACC_SUPER);
    }

    public boolean isInterface() {
        return 0 != (accessFlags & AccessFlags.ACC_INTERFACE);
    }

    public boolean isAbstract() {
        return 0 != (accessFlags & AccessFlags.ACC_ABSTRACT);
    }

    public boolean isSynthetic() {
        return 0 != (accessFlags & AccessFlags.ACC_SYNTHETIC);
    }

    public boolean isAnnotation() {
        return 0 != (accessFlags & AccessFlags.ACC_ANNOTATION);
    }

    public boolean isEnum() {
        return 0 != (this.accessFlags & AccessFlags.ACC_ENUM);
    }

    public boolean isAccessibleTo(Class other) {
        return this.isPublic() || this.getPackageName().equals(other.getPackageName());
    }

    public boolean isAssignableFrom(Class other) {
        if (this == other) return true;
        if (!other.isInterface()) {
            return this.isSubClassOf(other);
        } else {
            return this.isImplements(other);
        }
    }

    public boolean isSubClassOf(Class other) {
        for (Class c = this.superClass; c != null; c = c.superClass) {
            if (c == other) {
                return true;
            }
        }
        return false;
    }

    public boolean isImplements(Class other) {
        for (Class c = this; c != null; c = c.superClass) {
            for (Class clazz : c.interfaces) {
                if (clazz == other || clazz.isSubInterfaceOf(other)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSubInterfaceOf(Class iface) {
        for (Class superInterface : this.interfaces) {
            if (superInterface == iface || superInterface.isSubInterfaceOf(iface)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是数组
     */
    public boolean isArray() {
        return name.getBytes()[0] == '[';
    }

    public Field getField(String name, String descriptor, boolean isStatic) {
        for (Class c = this; c != null; c = c.superClass) {
            for (Field field : c.fields) {
                if (field.isStatic() == isStatic &&
                        field.name.equals(name) &&
                        field.descriptor.equals(descriptor)) {
                    return field;
                }
            }
        }
        return null;
    }

    public Method getMainMethod() {
        return this.getStaticMethod("main", "([Ljava/lang/String;)V");
    }

    private Method getStaticMethod(String name, String descriptor) {
        for (Method method : this.methods) {
            if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
                return method;
            }
        }
        return null;
    }

    public Method getClinitMethod() {
        return getStaticMethod("<clinit>", "()V");
    }

    /**
     * 返回与类对应的数组类
     */
    public Class getArrayClass() {
        String arrayClassName = ClassNameHelper.getArrayClassName(name);
        return loader.loadClass(arrayClassName);
    }

    /**
     * 返回数组类的元素类型
     */
    public Class getComponentClass() {
        String componentClassName = ClassNameHelper.getComponentClassName(name);
        return loader.loadClass(componentClassName);
    }

    public String getPackageName() {
        int i = this.name.lastIndexOf("/");
        if (i >= 0) return this.name;
        return "";
    }

    // getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
    }

    public String[] getInterfaceNames() {
        return interfaceNames;
    }

    public void setInterfaceNames(String[] interfaceNames) {
        this.interfaceNames = interfaceNames;
    }

    public RunTimeConstantPool getRunTimeConstantPool() {
        return runTimeConstantPool;
    }

    public void setRunTimeConstantPool(RunTimeConstantPool runTimeConstantPool) {
        this.runTimeConstantPool = runTimeConstantPool;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public Method[] getMethods() {
        return methods;
    }

    public void setMethods(Method[] methods) {
        this.methods = methods;
    }

    public ClassLoader getLoader() {
        return loader;
    }

    public void setLoader(ClassLoader loader) {
        this.loader = loader;
    }

    public Class getSuperClass() {
        return superClass;
    }

    public void setSuperClass(Class superClass) {
        this.superClass = superClass;
    }

    public Class[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Class[] interfaces) {
        this.interfaces = interfaces;
    }

    public int getInstanceSlotCount() {
        return instanceSlotCount;
    }

    public void setInstanceSlotCount(int instanceSlotCount) {
        this.instanceSlotCount = instanceSlotCount;
    }

    public int getStaticSlotCount() {
        return staticSlotCount;
    }

    public void setStaticSlotCount(int staticSlotCount) {
        this.staticSlotCount = staticSlotCount;
    }

    public Slots getStaticVars() {
        return staticVars;
    }

    public void setStaticVars(Slots staticVars) {
        this.staticVars = staticVars;
    }

    public boolean isInitStarted() {
        return initStarted;
    }

    public void setInitStarted(boolean initStarted) {
        this.initStarted = initStarted;
    }
}
