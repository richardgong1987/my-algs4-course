public class Solution {
	public String solution(int a, int b, int c) {
		int[] count = {a, b, c};
		StringBuilder sb = new StringBuilder();

		while (true) {
			int maxIndex = getMaxIndex(count);
			if (maxIndex == -1) {
				break;
			}

			char ch = (char) ('a' + maxIndex);

			if (sb.length() < 2 || sb.charAt(sb.length() - 1) != ch || sb.charAt(sb.length() - 2) != ch) {
				sb.append(ch);
				count[maxIndex]--;
			} else {
				int secondMaxIndex = getSecondMaxIndex(count, maxIndex);
				if (secondMaxIndex == -1) {
					break;
				}

				ch = (char) ('a' + secondMaxIndex);
				sb.append(ch);
				count[secondMaxIndex]--;
			}
		}

		return sb.toString();
	}

	private int getMaxIndex(int[] count) {
		int maxCount = 0;
		int maxIndex = -1;

		for (int i = 0; i < count.length; i++) {
			if (count[i] > maxCount) {
				maxCount = count[i];
				maxIndex = i;
			}
		}

		return (maxCount > 0) ? maxIndex : -1;
	}

	private int getSecondMaxIndex(int[] count, int maxIndex) {
		int secondMaxCount = 0;
		int secondMaxIndex = -1;

		for (int i = 0; i < count.length; i++) {
			if (i == maxIndex) {
				continue;
			}

			if (count[i] > secondMaxCount) {
				secondMaxCount = count[i];
				secondMaxIndex = i;
			}
		}

		return (secondMaxCount > 0) ? secondMaxIndex : -1;
	}

	public static void main(String[] args) {
		Solution solution = new Solution();
		System.out.printf(solution.solution(0, 1, 8));
	}
}



