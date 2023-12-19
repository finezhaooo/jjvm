import static java.lang.ClassLoader.getSystemClassLoader;

/**
 * @ClassName: Test
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/12/16 01:34
 */
public class Test {

    public static void main(String[] args) {
        try {
            Class.forName("");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
