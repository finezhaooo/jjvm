package cn.zhaooo.jvm.instructions.math.iinc;

import cn.zhaooo.jvm.instructions.base.BytecodeReader;
import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;
import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;

/**
 * 给局部变量表中的int变量增加常量值，局部变量表的索引和常量值都由指令的操作数提供
 */
public class IINC implements Instruction {

    public int idx;         //  局部变量表索引
    public int constVal;    //  常量值

    @Override
    public void fetchOperands(BytecodeReader reader) {
        idx = reader.readByte();
        constVal = reader.readByte();
    }

    @Override
    public void execute(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        int val = localVars.getInt(idx);
        val += constVal;
        localVars.setInt(idx, val);
    }
}
