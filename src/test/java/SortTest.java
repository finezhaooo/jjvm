/**
 * @ClassName: SortTest
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/12/25 18:25
 */
public class SortTest {
    public static void insertSort(int[] arr) {
        int len = arr.length;
        int i, j, k;
        for (i = 1; i < len; i++) {
            // 为a[i]在前面的a[0...i-1]有序区间中找一个合适的位置，然后a[i]插入a[j+1]
            for (j = i - 1; j >= 0; j--) {
                if (arr[j] < arr[i]) {
                    break;
                }
            }
            // 如找到了一个合适的位置
            if (j != i - 1) {
                // 将比a[i]大的数据向后移，将[j+1,i-1]移动到[j+2,i]，然后将a[i]放入[j+1]
                int temp = arr[i];
                for (k = i - 1; k > j; k--) {
                    arr[k + 1] = arr[k];
                }
                // 将a[i]放到正确位置上
                arr[k + 1] = temp;
            }
        }
    }


    public static void main(String[] args) {
        int[] arr = new int[]{3, 8, 6, 2, 1, 5, 7, 4};
        insertSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
