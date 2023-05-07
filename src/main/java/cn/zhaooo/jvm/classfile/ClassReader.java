package cn.zhaooo.jvm.classfile;

import java.math.BigInteger;

/**
 * @author zhaooo3
 * @description: TODO
 * @date 4/23/23 6:20 PM
 */
public class ClassReader {
    private byte[] data;

    public ClassReader(byte[] data) {
        this.data = data;
    }

    public int readU1() {
        byte[] val = readByte(1);
        String strHex = new BigInteger(1, val).toString(16);
        return Integer.parseInt(strHex, 16);
    }

    public int readU2() {
        byte[] val = readByte(2);
        String strHex = new BigInteger(1, val).toString(16);
        return Integer.parseInt(strHex, 16);
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
        String strHex = new BigInteger(1, val).toString(16);
        return Long.parseLong(strHex, 16);
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

    private byte[] readByte(int length) {
        byte[] copy = new byte[length];
        System.arraycopy(data, 0, copy, 0, length);
        System.arraycopy(data, length, data, 0, data.length - length);
        return copy;
    }
}
