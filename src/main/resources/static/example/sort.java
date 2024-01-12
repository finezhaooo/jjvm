public class sort {
    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 5, 4};
        quickSort sort = new quickSort();
        sort.sort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }
}