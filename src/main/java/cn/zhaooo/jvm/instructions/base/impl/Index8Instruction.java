package cn.zhaooo.jvm.instructions.base.impl;

import cn.zhaooo.jvm.instructions.base.BytecodeReader;
import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;

/**
 * 根据索引存取局部变量表，索引由单字节操作数给出
 */
public class Index8Instruction implements Instruction {

    public int idx; //  局部变量表索引

    @Override
    public void fetchOperands(BytecodeReader reader) {
        idx = reader.readByte();
    }

    @Override
    public void execute(Frame frame) {

    }
}
