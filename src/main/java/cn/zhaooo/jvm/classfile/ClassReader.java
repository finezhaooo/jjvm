package cn.zhaooo.jvm.classfile;

import java.math.BigInteger;

/**
 * @author zhaooo3
 * @description: class文件读取器
 * @date 4/23/23 6:20 PM
 */
public class ClassReader {
    // 当前指针
    private int index;
    private final byte[] data;

    public ClassReader(byte[] data) {
        this.data = data;
    }

    public int readU1() {
        byte[] val = readByte(1);
        return new BigInteger(1, val).intValue();
    }

    public int readU2() {
        byte[] val = readByte(2);
        return new BigInteger(1, val).intValue();
    }

    public int[] readU2s() {
        int n = this.readU2();
        int[] s = new int[n];
        for (int i = 0; i < n; i++) {
            s[i] = this.readU2();
        }
        return s;
    }

    public long readU4() {
        byte[] val = readByte(4);
        return new BigInteger(1, val).longValue();
    }

    public int readU4ToInteger() {
        byte[] val = readByte(4);
        return new BigInteger(1, val).intValue();
    }

    public float readU8ToFloat() {
        byte[] val = readByte(8);
        return new BigInteger(1, val).floatValue();
    }

    public long readU8ToLong() {
        byte[] val = readByte(8);
        return new BigInteger(1, val).longValue();
    }

    public double readU8ToDouble() {
        byte[] val = readByte(8);
        return new BigInteger(1, val).doubleValue();
    }

    public byte[] readBytes(int n) {
        return readByte(n);
    }

    /**
     * 依次读取length个byte
     */
    private byte[] readByte(int length) {
        byte[] copy = new byte[length];
        // 将data的[index,index+length)拷贝到copy
        System.arraycopy(data, index, copy, 0, length);
        index += length;
        return copy;
    }
}
