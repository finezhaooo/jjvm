package cn.zhaooo.jvm.instructions.base;

/**
 * @ClassName: BytecodeReader
 * @Description: 读取字节码
 * @Author: zhaooo
 * @Date: 2023/08/21 14:17
 */
public class BytecodeReader {
    private byte[] codes;   //  字节码
    private int pc;         //  记录读取的字节位置

    public void reset(byte[] codes, int pc) {
        this.codes = codes;
        this.pc = pc;
    }

    /**
     * 读取1字节
     */
    public byte readByte() {
        byte code = codes[pc];
        pc++;
        return code;
    }

    /**
     * 读取2字节
     */
    public short readShort() {
        byte byte1 = readByte();
        byte byte2 = readByte();
        return (short) ((byte1 << 8) | byte2);
    }

    /**
     * 读取4字节
     */
    public int readInt() {
        int byte1 = this.readByte();
        int byte2 = this.readByte();
        int byte3 = this.readByte();
        int byte4 = this.readByte();
        return (byte1 << 24) | (byte2 << 16) | (byte3 << 8) | byte4;
    }

    public int[] readInts(int n) {
        int[] ints = new int[n];
        for (int i = 0; i < n; i++) {
            ints[i] = readInt();
        }
        return ints;
    }

    public void skipPadding() {
        while (pc % 4 != 0) {
            readByte();
        }
    }

    public int getPC() {
        return pc;
    }
}
