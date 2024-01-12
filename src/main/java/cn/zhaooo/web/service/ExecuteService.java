package cn.zhaooo.web.service;

import cn.zhaooo.jvm.ClassLoader;
import cn.zhaooo.jvm.Interpreter;
import cn.zhaooo.jvm.classfile.ClassFile;
import cn.zhaooo.jvm.classpath.Classpath;
import cn.zhaooo.jvm.rdta.Thread;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.tools.LogTool;
import cn.zhaooo.web.compile.StringSourceCompiler;
import cn.zhaooo.web.execute.WebLoader;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @ClassName: ExecuteService
 * @Description:
 * @Author: zhaooo
 * @Date: 2024/01/11 20:46
 */
@Service
public class ExecuteService {
    // 加载系统类
    private final static ClassLoader classLoader = new ClassLoader(new Classpath(null, null));
    private static final Cache<String, Thread> threadCache = CacheBuilder.newBuilder()
            .expireAfterWrite(10 * 60, TimeUnit.SECONDS)
            .build();
    /* 客户端发来的程序的运行时间限制 */
    private static final int RUN_TIME_LIMITED = 15;

    /* N_THREAD = N_CPU + 1，因为是 CPU 密集型的操作 */
    private static final int N_THREAD = 3;
    private static final String WAIT_WARNING = "server busy, please try again later.";
    private static final String NO_OUTPUT = "Nothing.";


    /* 负责执行客户端代码的线程池，根据《Java 开发手册》不可用 Executor 创建，有 OOM 的可能 */
    private static final ExecutorService pool = new ThreadPoolExecutor(N_THREAD, N_THREAD,
            0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(N_THREAD));

    public String execute(String mainClassName, Map<String, String> sourceCode) {
        // 编译结果收集器
        DiagnosticCollector<JavaFileObject> compileCollector = new DiagnosticCollector<>();
        // 编译源代码
        Map<String, byte[]> classBytes = StringSourceCompiler.compileAll(sourceCode, compileCollector);
        // 编译不通过，获取并返回编译错误信息
        if (classBytes == null || classBytes.isEmpty()) {
            return compileErrorInfo(compileCollector.getDiagnostics());
        }
        Callable<String> runTask = () -> {
            WebLoader webLoader = new WebLoader(classLoader);
            webLoader.getByteMap().putAll(classBytes);
            Class mainClass = webLoader.loadClass(mainClassName.replace(".", "/"));
            if (mainClass == null) {
                return "Can't find or load main class.";
            }
            StringBuilder out = new StringBuilder();
            new Interpreter(mainClass.getMainMethod(), false, null, out);
            return out.toString();
        };

        Future<String> res;
        try {
            res = pool.submit(runTask);
        } catch (RejectedExecutionException e) {
            return WAIT_WARNING;
        }

        // 获取运行结果，处理非客户端代码错误
        String runResult;
        try {
            runResult = res.get(RUN_TIME_LIMITED, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            runResult = "Program interrupted.";
        } catch (ExecutionException | TimeoutException e) {
            runResult = e.getCause().getMessage();
        } finally {
            res.cancel(true);
        }

        return runResult != null ? runResult : NO_OUTPUT;
    }

    public String step(String uid, String mainClassName, Map<String, String> sourceCode) {
        Thread thread = threadCache.getIfPresent(uid);
        if (thread == null) {
            DiagnosticCollector<JavaFileObject> compileCollector = new DiagnosticCollector<>();
            // 编译源代码
            Map<String, byte[]> classBytes = StringSourceCompiler.compileAll(sourceCode, compileCollector);
            // 编译不通过，获取并返回编译错误信息
            if (classBytes == null || classBytes.isEmpty()) {
                return compileErrorInfo(compileCollector.getDiagnostics());
            }
            WebLoader webLoader = new WebLoader(classLoader);
            webLoader.getByteMap().putAll(classBytes);
            Class mainClass = webLoader.loadClass(mainClassName.replace(".", "/"));
            if (mainClass == null) {
                return "Can't find or load main class.";
            }
            thread = Interpreter.createThread(mainClass.getMainMethod(), new StringBuilder());
            threadCache.put(uid, thread);
        }
        if (thread.isStackEmpty()) {
            threadCache.invalidate(uid);
            return thread.getOut().toString();
        }
        return Interpreter.step(thread);
    }

    public String stop(String uid) {
        threadCache.invalidate(uid);
        return "Stop.";
    }

    public String compile(Map<String, String> sourceCode) {
        // 编译结果收集器
        DiagnosticCollector<JavaFileObject> compileCollector = new DiagnosticCollector<>();
        // 编译源代码
        Map<String, byte[]> classBytes = StringSourceCompiler.compileAll(sourceCode, compileCollector);
        // 编译不通过，获取并返回编译错误信息
        if (classBytes == null || classBytes.isEmpty()) {
            return compileErrorInfo(compileCollector.getDiagnostics());
        }
        byte[] mainClassBytes = classBytes.get(sourceCode.keySet().iterator().next());
        ClassFile classFile = new ClassFile(mainClassBytes);
        return LogTool.printClassInfo(classFile);
    }

    private String compileErrorInfo(List<Diagnostic<? extends JavaFileObject>> compileError) {
        StringBuilder compileErrorRes = new StringBuilder();
        for (Diagnostic<? extends JavaFileObject> diagnostic : compileError) {
            compileErrorRes.append(diagnostic.getKind()).append(":")
                    .append(diagnostic.getSource().getName()).append(":")
                    .append(diagnostic.getLineNumber()).append("\t")
                    .append(diagnostic.getMessage(null))
                    .append(System.lineSeparator());
        }
        return compileErrorRes.toString();
    }
}
