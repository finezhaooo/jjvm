package cn.zhaooo.jvm.instructions.base;

import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Method;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.Thread;

/**
 * @ClassName: ClassInitLogic
 * @Description: 类初始化
 * @Author: zhaooo
 * @Date: 2023/08/21 14:23
 */
public class ClassInitLogic {
    public static void initClass(Thread thread, Class clazz) {
        //  将类的initStarted状态设置成true
        clazz.startInit();
        scheduleClinit(thread, clazz);
        initSuperClass(thread, clazz);
    }

    private static void scheduleClinit(Thread thread, Class clazz) {
        //  类初始化方法
        Method clinit = clazz.getClinitMethod();
        if (null == clinit) return;
        Frame newFrame = thread.newFrame(clinit);
        thread.pushFrame(newFrame);
    }

    private static void initSuperClass(Thread thread, Class clazz) {
        if (clazz.isInterface()) return;
        Class superClass = clazz.getSuperClass();
        if (null != superClass && !superClass.getInitStarted()) {
            initClass(thread, superClass);
        }
    }
}
