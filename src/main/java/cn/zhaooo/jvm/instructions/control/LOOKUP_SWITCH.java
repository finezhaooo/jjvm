package cn.zhaooo.jvm.instructions.control;

import cn.zhaooo.jvm.instructions.base.BytecodeReader;
import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

import java.util.Arrays;

/**
 * Java语言中的switch-case语句有两种实现方式：如果case值可以编码成一个索引表，则实现成tableswitch指令；否则实现成lookupswitch指令。
 */
public class LOOKUP_SWITCH implements Instruction {

    private int defaultOffset;  //  默认情况下执行跳转所需的字节码偏移量
    private int npairs;
    private int[] matchOffsets; //  类似Map，key是case值，value是跳转偏移量

    @Override
    public void fetchOperands(BytecodeReader reader) {
        reader.skipPadding();
        defaultOffset = reader.readInt();
        npairs = reader.readInt();
        matchOffsets = reader.readInts(npairs * 2);
    }

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        int key = operandStack.popInt();

        //  从操作数栈中弹出一个int变量，然后用它查找matchOffsets，看是否能找到匹配的key
        //  hotspot是二分查找，时间复杂度是O(logN)
        for (int i = 0; i < npairs * 2; i += 2) {
            //  如果能，按照value给出的偏移量跳转
            if (matchOffsets[i] == key) {
                int offset = matchOffsets[i + 1];
                Instruction.branch(frame, offset);
                return;
            }
        }
        //  否则按照defaultOffset跳转
        Instruction.branch(frame, defaultOffset);
    }

}
