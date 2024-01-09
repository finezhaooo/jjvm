import cn.zhaooo.jvm.ClassLoader;
import cn.zhaooo.jvm.Interpreter;
import cn.zhaooo.jvm.classfile.ClassFile;
import cn.zhaooo.jvm.classpath.Classpath;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.tools.LogTool;
import cn.zhaooo.web.compile.StringSourceCompiler;
import cn.zhaooo.web.execute.WebLoader;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.util.*;

/**
 * @ClassName: CompilerTest
 * @Description:
 * @Author: zhaooo
 * @Date: 2024/01/08 15:22
 */
public class CompilerTest {
    public static void main(String[] args) {
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        Map<String, String> strings = new HashMap<>();
        strings.put("hello", "public class hello{public static void main(String[] args){System.out.println(\"hello world\");}}");
        strings.put("hello2", "public class hello2{public static void main(String[] args){System.out.println(\"hello world\");}}");
        Map<String, byte[]> code = StringSourceCompiler.compile(strings, collector);
        if (code == null || code.isEmpty()) {
            List<Diagnostic<? extends JavaFileObject>> compileError = collector.getDiagnostics();
            StringBuilder compileErrorRes = new StringBuilder();
            for (Diagnostic<? extends JavaFileObject> diagnostic : compileError) {
                compileErrorRes.append(diagnostic.getKind()).append(":");
                compileErrorRes.append(diagnostic.getSource().getName()).append(":");
                compileErrorRes.append(diagnostic.getLineNumber()).append("\t");
                compileErrorRes.append(diagnostic.getMessage(null));
                compileErrorRes.append(".");
                compileErrorRes.append(System.lineSeparator());
            }
            System.out.println(compileErrorRes);
            return;
        }
        ClassFile classFile = new ClassFile(code.get("hello"));
        LogTool.printClassInfo(classFile);
        ClassLoader classLoader = new ClassLoader(new Classpath(null, null));
        WebLoader webLoader = new WebLoader(classLoader);
        webLoader.getByteMap().putAll(code);
        Class mainClass = webLoader.loadClass("hello");
        new Interpreter(mainClass.getMainMethod(), true, null);
    }
}
