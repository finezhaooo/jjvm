package cn.zhaooo.jvm.instructions.base.impl;

import cn.zhaooo.jvm.instructions.base.BytecodeReader;
import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;

/**
 * 访问运行时常量池，常量池索引由2字节操作数给出
 */
public class Index16Instruction implements Instruction {

    protected int idx;  //  常量池索引

    @Override
    public void fetchOperands(BytecodeReader reader) {
        // 2 bytes
        idx = reader.readShort();
    }

    @Override
    public void execute(Frame frame) {

    }
}
