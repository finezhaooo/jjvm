package cn.zhaooo.jvm.instructions.references;

import cn.zhaooo.jvm.instructions.base.BytecodeReader;
import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.rdta.heap.constantpool.ClassRef;
import cn.zhaooo.jvm.rdta.heap.constantpool.RunTimeConstantPool;
import cn.zhaooo.jvm.rdta.heap.methodarea.Class;
import cn.zhaooo.jvm.rdta.heap.methodarea.Object;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * 创建多维数组
 */
public class MULTI_ANEW_ARRAY implements Instruction {

    private short idx;          //  运行时常量池索引，用以寻找类符号引用
    private byte dimensions;    //  数组维度

    @Override
    public void fetchOperands(BytecodeReader reader) {
        idx = reader.readShort();
        dimensions = reader.readByte();
    }

    @Override
    public void execute(Frame frame) {
        RunTimeConstantPool runTimeConstantPool = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) runTimeConstantPool.getConstants((int) idx);
        Class arrClass = classRef.getResolvedClass();

        OperandStack stack = frame.getOperandStack();
        int[] counts = popAndCheckCounts(stack, dimensions);
        Object arr = newMultiDimensionalArray(counts, arrClass);
        stack.pushRef(arr);
    }

    /**
     * 从操作数栈中弹出n个int值，确保它们都大于等于0
     */
    private int[] popAndCheckCounts(OperandStack stack, int dimensions) {
        int[] counts = new int[dimensions];
        for (int i = dimensions - 1; i >= 0; i--) {
            counts[i] = stack.popInt();
            if (counts[i] < 0) {
                throw new NegativeArraySizeException();
            }
        }

        return counts;
    }

    /**
     * 创建多维数组
     */
    private Object newMultiDimensionalArray(int[] counts, Class arrClass) {
        int count = counts[0];
        Object arr = arrClass.newArray(count);
        if (counts.length > 1) {
            Object[] refs = arr.getRefs();
            for (int i = 0; i < refs.length; i++) {
                int[] copyCount = new int[counts.length - 1];
                System.arraycopy(counts, 1, copyCount, 0, counts.length - 1);
                refs[i] = newMultiDimensionalArray(copyCount, arrClass.getComponentClass());
            }
        }

        return arr;
    }
}
