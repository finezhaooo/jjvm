import cn.zhaooo.jvm.classfile.ClassFile;
import cn.zhaooo.jvm.classpath.Classpath;
import cn.zhaooo.jvm.classpath.Entry;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Spliterators;
import java.util.stream.Stream;

/**
 * @ClassName: ClasspathTest
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/12/17 17:00
 */
public class ClasspathTest {
    public static void main(String[] args) {
        Classpath classpath = new Classpath(null, null);
        try {
            Method getDir  = Classpath.class.getDeclaredMethod("getJreDir", String.class);
            getDir.setAccessible(true);
            String jrePath = getDir.invoke(classpath, (Object) null) + File.separator + "*";
            Entry entry = Entry.create(jrePath);
            String[] libs = entry.toString().split(File.pathSeparator);
            for (String lib : libs) {
                System.out.println(lib);
            }
            ClassFile classFile = new ClassFile(entry.readClass("java/lang/Object.class"));
            ClassFileTest.printClassInfo(classFile);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
