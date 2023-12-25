package cn.zhaooo.jvm.instructions.base;

import cn.zhaooo.jvm.rdta.heap.methodarea.Method;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.Thread;
import cn.zhaooo.jvm.rdta.jvmstack.Slot;

/**
 * @ClassName: MethodInvokeLogic
 * @Description: 在定位到需要调用的方法之后，Java虚拟机要给这个方法创建一个新的帧并把它推入Java虚拟机栈顶，然后传递参数
 * @Author: zhaooo
 * @Date: 2023/08/21 14:20
 */
public class MethodInvokeLogic {
    /**
     * 调用方法
     * 参数的传递过程，其实就是将调用者栈帧的操作数栈中 pop 出数据，放入到被调用者栈帧的局部变量表中
     */
    public static void invokeMethod(Frame invokerFrame, Method method) {
        // 创建新的帧并推入Java虚拟机栈
        Thread thread = invokerFrame.getThread();
        Frame newFrame = thread.newFrame(method);
        thread.pushFrame(newFrame);

        // 确定方法的参数在局部变量表中占用多少位置，
        // 传递给调用的方法需要按照 index 对应，第一个参数设置到 index 为 0 的位置
        // 实例方法，通常 index 为 0 的位置是 this
        int argSlotCount = method.getArgSlotCount();
        if (argSlotCount > 0) {
            //  依次将参数从调用者的操作数栈中弹出，放进被调用方法的局部变量表中
            for (int i = argSlotCount - 1; i >= 0; i--) {
                Slot slot = invokerFrame.getOperandStack().popSlot();
                newFrame.getLocalVars().setSlot(i, slot);
            }
        }

        //  hack
        if (method.isNative()) {
            if ("registerNatives".equals(method.getName())) {
                thread.popFrame();
            } else {
                throw new RuntimeException("native method " + method.getName());
            }
        }
    }
}
