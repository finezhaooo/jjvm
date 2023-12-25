import static java.lang.ClassLoader.getSystemClassLoader;

/**
 * @ClassName: Test
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/12/16 01:34
 */
public class Test {
    public static void main(String[] args) {
        int a = 10;
        switch (a) {
            case 10:
                System.out.println("1");
                break;
            case 11:
                System.out.println("2");
                break;
        }
    }
}
