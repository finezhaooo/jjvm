/**
 * @ClassName: LambdaExample
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/12/16 00:58
 */
interface MyFunction {
    void apply();
}

public class LambdaExample {
    public static void main(String[] args) {
        MyFunction lambda = () -> System.out.println("Hello from Lambda!");
        lambda.apply();
    }
}
