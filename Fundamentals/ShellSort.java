import java.util.Arrays;

public class ShellSort {
	public static void sort(int[] arr) {
		int n = arr.length;

		// Start with a large gap and reduce it in each iteration
		for (int gap = n / 2; gap > 0; gap /= 2) {

			// Do a gapped insertion sort for this gap size
			for (int i = gap; i < n; i++) {
				int temp = arr[i];

				// Shift elements up until the correct position for temp is found
				int j;
				for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
					arr[j] = arr[j - gap];
				}

				// Insert the temp element at the correct position
				arr[j] = temp;
			}
		}
	}

	public static void main(String[] args) {
		int[] arr = {12, 34, 54, 2, 3};
		System.out.println("Before sorting: " + Arrays.toString(arr));

		ShellSort.sort(arr);

		System.out.println("After sorting: " + Arrays.toString(arr));
	}
}
