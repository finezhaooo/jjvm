/**
 * @ClassName: TestLam
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/12/24 21:38
 */
public class TestLam {

    public static void main(String[] args) throws Throwable {
        long c = System.currentTimeMillis();
        final long b = c;
        Runnable runnable = () -> {
            long x = 10;
            long a = b + 20 * x;
        };
        runnable.run();
    }
}
