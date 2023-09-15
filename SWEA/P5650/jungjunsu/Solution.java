/*
 * 구현방법
 * 게임 진행행 함수, 좌표 클래스 등으로 모듈화 해서 시뮬레이션 구현하였습니다.
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
	static class Point {
		int x, y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static class Ball extends Point{
		int v;
		public Ball(int x, int y, int v) {
			super(x, y);
			this.v = v;
		}
		@Override
		public String toString() {
			return "Ball [x=" + x + ", y=" + y + ", v=" + v + "]";
		}
	}

	static int N;
	static int[][] map;
	static Deque<Integer[]> Q = new ArrayDeque<>();
	static Point[] wormhole = new Point[10];
	static long Ans;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		// -1 : 블랙홀, 0 : 빈공간, 1 ~ 5 : 블록, 6 ~ 10 : 웜홀
		int T = Integer.parseInt(in.readLine());
		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(in.readLine().trim());
			map = new int[N+2][N+2];
			Q = new ArrayDeque<>();
			wormhole = new Point[10];
			Ans = 0;

			for (int r = 1; r <= N; r++) {
				st = new StringTokenizer(in.readLine().trim());
				for (int c = 1; c <= N; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
					// 완탐해볼 좌표 Q에 저장
					if (map[r][c] == 0) Q.add(new Integer[] {c, r});
					// 웜홀 좌표 저장
					else if (map[r][c] > 5) {
						if (wormhole[map[r][c] - 6] == null) {
							wormhole[map[r][c] - 6] = new Point(c, r);
						}
						else {
							wormhole[map[r][c] - 1] = new Point(c, r);
						}
					}
				}
			}

			int size = Q.size();
			Ball ball = new Ball(-1, -1, -1);
			for (int cases = 0; cases < size; cases++) {
				Integer[] pos = Q.poll();
				int startX = pos[0];
				int startY = pos[1];
				ball.x = startX;
				ball.y = startY;
				// 사방 탐색
				for (int game = 0; game < 4; game++) {
					// 해당 방향에 게임 진행
					ball.v = game;
					game(ball, startX, startY, game);
				}
			}
			bw.write("#"+tc+" "+Ans+"\n");
		}
		bw.flush();
	}
	
	static int[][] dm = {{1,0},{-1,0},{0,1},{0,-1}};
	static int[][] dm2 = {{-1,-1,-1,-1},{2,0,3,1},{1,2,3,0},{1,3,0,2},{3,0,1,2},{1,0,3,2}};
	private static void game(Ball ball, int startX, int startY, int game) {
		int cnt = 0;
		int nr = -1;
		int nc = -1;
		int score = 0;
		while(true) {
			// 블랙홀/시작위치 이라면
			if (nr != -1 && nc != -1 && ((ball.x == startX && ball.y == startY) || map[ball.y][ball.x] == -1)) {
				Ans = Math.max(Ans, score);
				break;
			}
			nr = ball.y + dm[ball.v][0];
			nc = ball.x + dm[ball.v][1];

			// 테두리 벽이라면
			if (nr < 0 || nc < 0 || nr > N+1 || nc > N+1) {
				score += 1;
				if (ball.v < 2) ball.v = ball.v == 0 ? 1 : 0;
				else ball.v = ball.v == 2 ? 3 : 2;			
				continue;
			}

			// 웜홀 이라면
			if (map[nr][nc] > 5) {
				Point change;
				int holeNum1 = map[nr][nc] - 6;
				int holeNum2 = map[nr][nc] - 1;
				if (wormhole[holeNum1].y == nr && wormhole[holeNum1].x == nc) 
					change = wormhole[holeNum2];
				else 
					change = wormhole[holeNum1];
				ball.x = change.x;
				ball.y = change.y;
				continue;
			}
			// 1 ~ 5 번 블럭이라면
			if (1 <= map[nr][nc] && map[nr][nc] <= 5) {
				score++;
				ball.v = dm2[map[nr][nc]][ball.v];
				ball.x = nc;
				ball.y = nr;
				continue;
			}
			ball.x = nc;
			ball.y = nr;
		}
		
	}


	/*
         테스트 함수
     */
	private static void print(int[][] map) {
		for (int[] y:
				map) {
			for (int x :
					y) {
				System.out.print(x + " ");
			} System.out.println();
		} System.out.println();
	}


}

