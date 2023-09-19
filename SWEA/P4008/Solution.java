/*
	구현방법:
	순열 완탐인데, 순열에서 중복되는 연산은 제외하는 방법으로 문제를 해결했습니다.
	ex) +1 +2 -3 = +2 +1 -3
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution {
	static int N;
	static int MIN, MAX;
	static int[] numbers;
	static int[] ops;
	static int Ans;
	static boolean[] v;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		int TC = Integer.parseInt(in.readLine());
		for (int tc = 1; tc <= TC; tc++) {
			N = Integer.parseInt(in.readLine());
			numbers = new int[N];
			ops = new int[N-1];
			v = new boolean[N-1];
			MIN = Integer.MAX_VALUE;
			MAX = Integer.MIN_VALUE;

			// +, -, *, / 연산의 갯수
			st = new StringTokenizer(in.readLine());
			int point = -1;
			for (int oper = 0; oper < 4; oper++) {
				int opn = Integer.parseInt(st.nextToken());
				for (int o = 0; o < opn; o++) {
					// + = 0, - = 1, * = 2, / = 3
					ops[++point] = oper;
				}
			}
			// 수식에 사용되는 숫자
			st = new StringTokenizer(in.readLine());
			for (int i = 0; i < N; i++) {
				numbers[i] = Integer.parseInt(st.nextToken());
			}

			permutation(0, numbers[0]);
			//System.out.println(MAX+" "+MIN);
			Ans = MAX - MIN;
			bw.write("#"+tc+" "+Ans+"\n");
		}
		bw.flush();
	}

	private static void permutation(int k, int num) {
		if (k == N - 1) {
			MIN = Math.min(MIN, num);
			MAX = Math.max(MAX, num);
			return;
		}

		boolean[] opcheck = new boolean[4];
		for (int i = 0; i < N-1; i++) {
			if (v[i] || opcheck[ops[i]]) continue;
			v[i] = true;
			opcheck[ops[i]] = true;

			int val = num;
			switch (ops[i]) {
				case 0:
					val += numbers[k+1];
					break;
				case 1:
					val -= numbers[k+1];
					break;
				case 2:
					val *= numbers[k+1];
					break;
				case 3:
					val /= numbers[k+1];
					break;
			}
			permutation(k+1, val);
			v[i] = false;
		}
	}


}

