public class quickSort {
    public int[] sort(int[] arr) {
        qsort(arr, 0, arr.length - 1);
        return arr;
    }

    private void qsort(int[] arr, int left, int right) {
        if (left < right) {
            int pivotPos = partition(arr, left, right);
            qsort(arr, left, pivotPos - 1);
            qsort(arr, pivotPos + 1, right);
        }
    }

    private int partition(int[] arr, int left, int right) {
        int pivot = arr[left];
        while (left < right) {
            while (left < right && arr[right] >= pivot) {
                right--;
            }
            arr[left] = arr[right];
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = pivot;
        return left;
    }
}