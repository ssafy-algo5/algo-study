/*
	구현 방법:
	BFS를 이용하여 큐에 위치(row, col), 경과시간(cnt), time(소용돌이 0, -1, 2==소멸)
	를 담아서 완전탐색 하였습니다. 도착지점에 갈 수 없다면 -1을 출력하도록 하였습니다.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Solution {

	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N;
	static int[][] map;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;


		int T = Integer.parseInt(in.readLine());

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(in.readLine());
			map = new int[N][N];

			for (int r = 0; r < N; r++) {
				st = new StringTokenizer(in.readLine());
				for (int c = 0; c < N; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
				}
			}
			// 시작 위치
			st = new StringTokenizer(in.readLine());
			int sy = Integer.parseInt(st.nextToken());
			int sx = Integer.parseInt(st.nextToken());
			// 종료 위치
			st = new StringTokenizer(in.readLine());
			int ey = Integer.parseInt(st.nextToken());
			int ex = Integer.parseInt(st.nextToken());

			int[] dr = {1, -1, 0, 0};
			int[] dc = {0, 0, 1, -1};
			boolean[][] v = new boolean[N][N];
			boolean endCheck = false;
			Deque<Integer[]> Q = new ArrayDeque<>();
			Q.add(new Integer[]{sy, sx, 0, -1});
			while(!Q.isEmpty()) {
				Integer[] pos = Q.poll();
				int y = pos[0];
				int x = pos[1];
				int cnt = pos[2];
				int time = pos[3];

				v[y][x] = true;

				// 도착지점
				if (y == ey && x == ex) {
					bw.write("#" + tc + " " + cnt + "\n");
					endCheck = true;
					break;
				}

				for (int i = 0; i < 4; i++) {
					int nr = y + dr[i];
					int nc = x + dc[i];

					if (nr < 0 || nc < 0 || nr >= N || nc >= N || v[nr][nc] || map[nr][nc] == 1) continue;
					if (map[nr][nc] == 2) {
						if (time + 1 < 2) Q.add(new Integer[]{y, x, cnt + 1, time + 1});
						else Q.add(new Integer[]{nr, nc, cnt + 1, -1});
					} else {
						if (time + 1 < 2) Q.add(new Integer[]{nr, nc, cnt + 1, time + 1});
						else Q.add(new Integer[]{nr, nc, cnt + 1, -1});
					}
				}

			}
			if (!endCheck) bw.write("#" + tc + " -1\n");
		}
		bw.flush();
	}

	private static void print(int[][] map) {
		for (int[] y : map) {
			for (int x : y) {
				System.out.print(x+" ");
			} System.out.println();
		} System.out.println();
	}

}