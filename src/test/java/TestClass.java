import cn.zhaooo.jvm.rdta.jvmstack.LocalVars;
import cn.zhaooo.jvm.rdta.jvmstack.OperandStack;

/**
 * @author zhaooo3
 * @description: TODO
 * @date 4/22/23 5:54 PM
 */
public class TestClass {

    public static void main(String[] args) {
        OperandStack stack = new OperandStack(10);
        stack.pushLong(Long.MAX_VALUE);
        System.out.println(stack.popLong());
        LocalVars localVars = new LocalVars(10);
        localVars.setLong(0, Long.MIN_VALUE);
        System.out.println(localVars.getLong(0));
    }
}
