package cn.zhaooo.jvm;

import com.alibaba.fastjson2.JSON;
import cn.zhaooo.jvm.instructions.Factory;
import cn.zhaooo.jvm.instructions.base.BytecodeReader;
import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.rdta.heap.methodarea.*;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.Thread;

/**
 * @ClassName: Interpreter
 * @Description: 解释器
 * @Author: zhaooo
 * @Date: 2023/08/21 15:52
 */
public class Interpreter {
    Interpreter(Method method, boolean logInst, String args) {
        //  创建一个Thread实例
        Thread thread = new Thread();
        //  创建一个帧并推入Java虚拟机栈
        Frame frame = thread.newFrame(method);
        thread.pushFrame(frame);

        if (null != args) {
            Object jArgs = createArgsArray(method.getClazz().getLoader(), args.split(" "));
            frame.getLocalVars().setRef(0, jArgs);
        }

        loop(thread, logInst);
    }

    /**
     * 将args参数转换成Java字符串数组
     */
    private Object createArgsArray(ClassLoader loader, String[] args) {
        Class stringClass = loader.loadClass("java/lang/String");
        Object argsArr = stringClass.getArrayClass().newArray(args.length);
        Object[] jArgs = argsArr.getRefs();
        for (int i = 0; i < jArgs.length; i++) {
            jArgs[i] = StringPool.jString(loader, args[i]);
        }
        return argsArr;
    }

    /**
     * 循环执行"计算pc、解码指令、执行指令"这三个步骤，直到遇到错误
     */
    private void loop(Thread thread, boolean logInst) {
        BytecodeReader reader = new BytecodeReader();
        while (true) {
            //  拿到当前帧
            Frame frame = thread.currentFrame();

            //  根据pc从当前方法中解码出一条指令
            int pc = frame.getNextPC();
            thread.setPC(pc);
            reader.reset(frame.getMethod().code, pc);
            byte opcode = reader.readByte();
            Instruction inst = Factory.create(opcode);

            if (null == inst) {
                System.err.println("Unsupported opcode " + byteToHexString(new byte[]{opcode}));
                break;
            }
            inst.fetchOperands(reader);
            frame.setNextPC(reader.getPC());

            if (logInst) {
                logInstruction(frame, inst, opcode);
            }

            //  执行
            inst.execute(frame);

            //  指令执行完毕之后，判断Java虚拟机栈中是否还有帧
            if (thread.isStackEmpty()) {
                break;
            }
        }
    }

    private static void logInstruction(Frame frame, Instruction inst, byte opcode) {
        Method method = frame.getMethod();
        String className = method.getClazz().getName();
        String methodName = method.getName();
        String outStr = (className + "." + methodName + "() \t") +
                "寄存器(指令)：" + byteToHexString(new byte[]{opcode}) +
                " -> " + inst.getClass().getSimpleName() +
                " => 局部变量表：" + JSON.toJSONString(frame.getLocalVars().getSlots()) +
                " 操作数栈：" + JSON.toJSONString(frame.getOperandStack().getSlots());
        System.out.println(outStr);
    }

    private static String byteToHexString(byte[] codes) {
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        for (byte b : codes) {
            int value = b & 0xFF;
            String strHex = Integer.toHexString(value);
            if (strHex.length() < 2) {
                strHex = "0" + strHex;
            }
            sb.append(strHex);
        }
        return sb.toString();
    }
}
