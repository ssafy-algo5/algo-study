/*
	구현방법:
	dfs를 이용하여 순열 완탐하는데, 약품을 투입하여 성능검사가 통과되면 완탐을 종료하였습니다.
 */
import java.io.*;
import java.util.StringTokenizer;

public class Solution {

	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	static int T;
	static int D, W, K;
	static int[][] map;
	static long Ans;


	public static void main(String[] args) throws IOException {
		T = Integer.parseInt(in.readLine());
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(in.readLine());
			// 3≤D≤13, 1≤W≤20, 1≤K≤D
			D = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			map = new int[D][W];
			Ans = Integer.MAX_VALUE;

			for (int r = 0; r < D; r++) {
				st = new StringTokenizer(in.readLine());
				for (int c = 0; c < W; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
				}
			}


			dfs(0, 0, new int[D]);
			bw.write("#"+tc+" "+Ans+"\n");
		}
		bw.flush();
	}

	private static void dfs(int row, int cnt, int[] sel) {
		if (cnt >= Ans) return;

		if (row == D) {
			if (test(sel)) Ans = Math.min(Ans, cnt);
			return;
		}

		for (int i = -1; i < 2; i++) {
			sel[row] = i;
			if (i == -1) dfs(row + 1, cnt, sel);
			else dfs(row + 1, cnt + 1, sel);
		}
	}

	private static boolean test(int[] sel) {
		boolean check = false;
		for (int w = 0; w < W; w++) {
			check = false;
			int aCount = 0;
			int bCount = 0;

			for (int d = 0; d < D; d++) {
				int type = sel[d] == -1 ? map[d][w] : sel[d];
				if (type == 0) {
					bCount = 0;
					aCount += 1;
				} else {
					aCount = 0;
					bCount += 1;
				}
				if (aCount == K || bCount == K) {
					check = true;
					break;
				}
			}
			if (!check) return false;
		}

		return true;
	}

}