package cn.zhaooo.jvm.instructions.extended;

import cn.zhaooo.jvm.instructions.base.BytecodeReader;
import cn.zhaooo.jvm.instructions.base.Instruction;
import cn.zhaooo.jvm.instructions.loads.aload.ALOAD;
import cn.zhaooo.jvm.instructions.loads.dload.DLOAD;
import cn.zhaooo.jvm.instructions.loads.fload.FLOAD;
import cn.zhaooo.jvm.instructions.loads.iload.ILOAD;
import cn.zhaooo.jvm.instructions.loads.lload.LLOAD;
import cn.zhaooo.jvm.instructions.math.iinc.IINC;
import cn.zhaooo.jvm.instructions.stores.astore.ASTORE;
import cn.zhaooo.jvm.instructions.stores.dstore.DSTORE;
import cn.zhaooo.jvm.instructions.stores.fstore.FSTORE;
import cn.zhaooo.jvm.instructions.stores.istore.ISTORE;
import cn.zhaooo.jvm.instructions.stores.lstore.LSTORE;
import cn.zhaooo.jvm.rdta.jvmstack.Frame;

/**
 * 加载类指令、存储类指令、ret指令和iinc指令需要按索引访问局部变量表，
 * 对于大部分方法来说，局部变量表大小都不会超过256，所以用一字节来表示索引就够了，
 * 但是如果有方法的局部变量表超过256，Java虚拟机规范通过wide指令来扩展前述指令。
 */
public class WIDE implements Instruction {

    private Instruction modifiedInstruction;    //  被改变的指令

    @Override
    public void fetchOperands(BytecodeReader reader) {
        //  读取一字节的操作码
        byte opcode = reader.readByte();
        switch (opcode){
            case 0x15:
                ILOAD inst_iload = new ILOAD();
                inst_iload.idx = reader.readShort();
                modifiedInstruction = inst_iload;
                break;

            case 0x16:
                LLOAD inst_lload = new LLOAD();
                inst_lload.idx = reader.readShort();
                modifiedInstruction = inst_lload;
                break;

            case 0x17:
                FLOAD inst_fload = new FLOAD();
                inst_fload.idx = reader.readShort();
                modifiedInstruction = inst_fload;
                break;

            case 0x18:
                DLOAD inst_dload = new DLOAD();
                inst_dload.idx = reader.readShort();
                modifiedInstruction = inst_dload;
                break;

            case 0x19:
                ALOAD inst_aload = new ALOAD();
                inst_aload.idx = reader.readShort();
                modifiedInstruction = inst_aload;
                break;

            case 0x36:
                ISTORE inst_istore = new ISTORE();
                inst_istore.idx = reader.readShort();
                modifiedInstruction = inst_istore;
                break;

            case 0x37:
                LSTORE inst_lstore = new LSTORE();
                inst_lstore.idx = reader.readShort();
                modifiedInstruction = inst_lstore;
                break;

            case 0x38:
                FSTORE inst_fstore = new FSTORE();
                inst_fstore.idx = reader.readShort();
                modifiedInstruction = inst_fstore;
                break;

            case 0x39:
                DSTORE inst_dstore = new DSTORE();
                inst_dstore.idx = reader.readShort();
                modifiedInstruction = inst_dstore;
                break;

            case 0x3a:
                ASTORE inst_astore = new ASTORE();
                inst_astore.idx = reader.readShort();
                modifiedInstruction = inst_astore;
                break;

            case (byte) 0x84:
                IINC inst_iinc = new IINC();
                inst_iinc.idx = reader.readShort();
                modifiedInstruction = inst_iinc;
                break;

            case (byte) 0xa9:   // ret指令
                throw new RuntimeException("Unsupported opcode: 0xa9!");
        }
    }

    @Override
    public void execute(Frame frame) {
        modifiedInstruction.execute(frame);
    }
}
