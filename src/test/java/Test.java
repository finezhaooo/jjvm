import java.util.Arrays;

/**
 * @ClassName: Test
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/12/16 01:34
 */
public class Test {
    public static void main(String[] args) {
        int[] arr = new int[]{3, 8, 6, 2, 1, 5, 7, 4};
        SortTest sortTest = new SortTest();
        sortTest.insertSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}

