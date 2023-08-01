import java.util.List;

/**
 * @author zhaooo3
 * @description: TODO
 * @date 4/22/23 5:54 PM
 */
public class TestClass<T> {
    static int A = 1;

    int inc(int a, int b, List<Integer> list) {
        int c = a + b;
        return c + list.get(0);
    }

    int add(int a) {
        return a + 1;
    }
}
