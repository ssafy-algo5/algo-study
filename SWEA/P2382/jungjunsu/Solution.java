/*
	구현방법:
	저는 Microbe라는 클래스에 좌표, 미생물의 수, 방향을 저장할 수 있게 하여 미생물 군집 객체를 만들었습니다.
	LinkedList에 Microbe 객체들을 담아서, M시간 동안에 미생물들의 변화를 시뮬레이션 해 주었습니다.
	Microbe는 미생물 수에 따라서 해당 list에 내림차순 저장되고, 시뮬레이션이 진행되는 동안에 같은 좌표에 온 다음 미생물 군집은
	무조건 미생물의 수가 작기 때문에 list에서 제외되도록 구현하였습니다.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Solution {
	static class Microbe implements Comparable<Microbe> {
		int r, c, n, d;

		Microbe(int r, int c, int n, int d) {
			this.r = r;
			this.c = c;
			this.n = n;
			this.d = d;
		}

		@Override
		public String toString() {
			return "Microbe{" +
					"r=" + r +
					", c=" + c +
					", n=" + n +
					", d=" + d +
					'}';
		}

		@Override
		public int compareTo(Microbe other) {
			return Integer.compare(other.n, this.n);
		}
	}

	static int N, M, K;
	static int[][][] map;
	static long Ans;
	static int[] dr = {0, -1, 1, 0, 0};
	static int[] dc = {0, 0, 0, -1, 1};
	static LinkedList<Microbe> list = new LinkedList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		int TC=Integer.parseInt(in.readLine());
		for (int tc = 1; tc <= TC; tc++) {
			st = new StringTokenizer(in.readLine());
			N = Integer.parseInt(st.nextToken()); // 5 ≤ N ≤ 100
			M = Integer.parseInt(st.nextToken()); // 1 ≤ M ≤ 1,000
			K = Integer.parseInt(st.nextToken()); // 5 ≤ K ≤ 1,000
			map = new int[N][N][2];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					map[i][j][0] = -1;
				}
			}
			list.clear(); // n : 1 이상 10,000
			Ans = 0;

			for (int m = 0; m < K; m++) {
				st = new StringTokenizer(in.readLine());
				int r = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				int n = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());

				list.add(new Microbe(r, c, n, d));
			}

			Collections.sort(list);
			while(M-- > 0) {
				for (int i = 0; i < list.size(); i++) {
					Microbe m = list.get(i);
					// m.d : 방향, m.r m.c : 위치, m.n : 미생물 군집의 미생물 수
					int d = m.d;
					int nr = m.r + dr[d];
					int nc = m.c + dc[d];
					int num = m.n;

					// 테두리라면
					if (nr == 0 || nc == 0 || nr == N - 1 || nc == N - 1) {
						// 미생물이 모두 죽는다면
						if (num == 1) {
							list.remove(i);
							i--;
						} else {
							m.n = num / 2;
							if (d > 2) {
								m.d = d == 3 ? 4 : 3;
							} else {
								m.d = d == 1 ? 2 : 1;
							}
							m.r = nr;
							m.c = nc;
						}
					} else {
						// 다른 미생물 군집이 있다면
						if (map[nr][nc][0] == M) {
							list.get(map[nr][nc][1]).n += m.n;
							list.remove(i);
							i--;
						} else {
							map[nr][nc][1] = i;
							map[nr][nc][0] = M;
							m.r = nr;
							m.c = nc;
						}
					}
				} Collections.sort(list);
			}

			for (int i = 0; i < list.size(); i++) {
				Ans += list.get(i).n;
			}

			bw.write("#"+tc+" "+Ans+"\n");
		}
		bw.flush();
	}


}

/*
1
7 2 9
1 1 7 1
2 1 7 1
5 1 5 4
3 2 8 4
4 3 14 1
3 4 3 3
1 5 8 2
3 5 100 1
5 5 1 1
 */