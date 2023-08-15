package cn.zhaooo.jvm.rdta.heap.util;

import cn.zhaooo.jvm.rdta.heap.methodarea.MethodDescriptor;

/**
 * @ClassName: MethodDescriptorParser
 * @Description: 方法描述符解析
 * @Author: zhaooo
 * @Date: 2023/08/07 23:46
 */
public class MethodDescriptorParser {

    private String raw;
    private int offset;
    private MethodDescriptor parsed;

    public static MethodDescriptor parseMethodDescriptorParser(String descriptor) {
        MethodDescriptorParser parser = new MethodDescriptorParser();
        return parser.parse(descriptor);
    }

    public MethodDescriptor parse(String descriptor) {
        raw = descriptor;
        parsed = new MethodDescriptor();
        startParams();
        parseParamTypes();
        endParams();
        parseReturnType();
        finish();
        return parsed;
    }

    private void startParams() {
        if (readUint8() != '(') {
            causePanic();
        }
    }

    private void endParams() {
        if (readUint8() != ')') {
            causePanic();
        }
    }

    public void parseParamTypes() {
        while (true) {
            String type = parseFieldType();
            if ("".equals(type)) break;
            parsed.addParameterType(type);
        }
    }

    public void parseReturnType() {
        // void
        if (readUint8() == 'V'){
            parsed.returnType = "V";
            return;
        }
        unreadUint8();
        String type = parseFieldType();
        if (!"".equals(type)){
            parsed.returnType = type;
            return;
        }
        causePanic();
    }

    public void finish(){
        if (offset != raw.length()){
            causePanic();
        }
    }

    public byte readUint8() {
        byte[] bytes = raw.getBytes();
        byte b = bytes[offset];
        offset++;
        return b;
    }

    public void unreadUint8() {
        offset--;
    }

    public String parseFieldType() {
        switch (this.readUint8()) {
            case 'B':
                return "B";
            case 'C':
                return "C";
            case 'D':
                return "D";
            case 'F':
                return "F";
            case 'I':
                return "I";
            case 'J':
                return "J";
            case 'S':
                return "S";
            case 'Z':
                return "Z";
            case 'L':
                return parseObjectType();
            case '[':
                return parseArrayType();
            default:
                unreadUint8();
                return "";
        }
    }

    public void causePanic() {
        throw new RuntimeException("BAD descriptor：" + raw);
    }

    private String parseObjectType() {
        String unread = raw.substring(offset);
        int semicolonIndx = unread.indexOf(";");
        if (semicolonIndx == -1) {
            causePanic();
            return "";
        }
        int objStart = offset - 1;
        int ojbEnd = offset + semicolonIndx + 1;
        offset = ojbEnd;
        // descriptor
        return raw.substring(objStart, ojbEnd);
    }

    private String parseArrayType() {
        int arrStart = offset - 1;
        // 如果多重数组 递归调用
        this.parseFieldType();
        int arrEnd = offset;
        //descriptor
        return raw.substring(arrStart, arrEnd);
    }
}
