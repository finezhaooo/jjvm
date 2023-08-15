package cn.zhaooo.jvm.rdta.heap.methodarea;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MethodDescriptor
 * @Description: 方法描述符
 * @Author: zhaooo
 * @Date: 2023/08/07 17:41
 */
public class MethodDescriptor {
    public List<String> parameterTypes = new ArrayList<>(); //  参数类型列表
    public String returnType;                               //  返回值类型

    public void addParameterType(String type) {
        parameterTypes.add(type);
    }
}
