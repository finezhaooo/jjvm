package cn.zhaooo.jvm.instructions.control;

import cn.zhaooo.jvm.instructions.base.BytecodeReader;
import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

import java.util.Arrays;


/**
 * Java语言中的switch-case语句有两种实现方式：如果case值可以编码成一个索引表，则实现成tableswitch指令；否则实现成lookupswitch指令。
 */
public class TABLE_SWITCH implements Instruction {

    private int defaultOffset;  //  默认情况下执行跳转所需的字节码偏移量
    private int low;            //  case的取值范围
    private int high;           //  case的取值范围
    private int[] jumpOffsets;  //  索引表

    @Override
    public void fetchOperands(BytecodeReader reader) {
        //  table switch指令操作码的后面有0~3字节的padding，
        //  以保证defaultOffset在字节码中的地址是4的倍数
        reader.skipPadding();
        defaultOffset = reader.readInt();

        //  索引表里存放high-low+1个int值，对应各种case情况下执行跳转所需的字节码偏移量
        low = reader.readInt();
        high = reader.readInt();
        int jumpOffsetCount = high - low + 1;
        jumpOffsets = reader.readInts(jumpOffsetCount);
    }

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        int idx = operandStack.popInt();
        int offset;

        //  从操作数栈中弹出一个int变量，看它是否在low和high给定的范围之内
        if (idx >= low && idx <= high) {
            //  如果在，则从jumpOffsets表中查出偏移量进行跳转
            //  时间复杂度是O(1)
            offset = jumpOffsets[idx - low];
        } else {
            //  否则，按照defaultOffset跳转
            offset = defaultOffset;
        }
        Instruction.branch(frame, offset);
    }
}
