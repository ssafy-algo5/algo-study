/*
	구현 방법:
	서로소 집합을 이용하여, 사람 관계 M개에 대해서 union을 한 뒤에 무리의 갯수를 세어주었습니다.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution {
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, M;
	static int[] parents;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(in.readLine());
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(in.readLine());
			// N : 사람 수, M : 관계 수
			//N, M(1 ≤ N ≤ 100, 0 ≤ M ≤ N(N-1)/2)
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			parents = new int[N+1];

			// 가장 작은 서로소 집합
			for (int i = 1; i <= N; i++) {
				parents[i] = i;
			}

			// union
			for (int relation = 0; relation < M; relation++) {
				st = new StringTokenizer(in.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());

				unionSet(x, y);
			}
			// 무리 수 세기
			int cnt = 0;
			boolean[] check = new boolean[N+1];
			for (int i = 1; i <= N; i++) {
				if (check[findSet(i)]) continue;
				cnt++;
				check[findSet(i)] = true;
			}
			System.out.printf("#%d %d\n", tc, cnt);
		}
	}

	private static int findSet(int x) {
		if (parents[x] == x) return parents[x];
		return x = findSet(parents[x]);
	}

	private static boolean unionSet(int x, int y) {
		int xParent = findSet(x);
		int yParent = findSet(y);
		if (xParent == yParent) return false;
		parents[yParent] = xParent;
		return true;
	}

}

