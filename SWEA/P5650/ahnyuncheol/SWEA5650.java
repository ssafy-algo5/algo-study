import java.io.*;
import java.util.*;

public class SWEA5650 {
	static int[][] board;
	static int N;
	static int max = Integer.MIN_VALUE;
	
	static int[] ddr = {0, 1, 0, -1};
	static int[] ddc = {1, 0, -1, 0};
	
	public static void main(String[] args) throws IOException {

		System.setIn(new FileInputStream("in"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine().trim());
		for (int xx = 1; xx <= T; xx++) {
			N = Integer.parseInt(br.readLine().trim());
			board = new int[N][N];
			max = 0;
			
			for (int r = 0; r < N; r++) {
				st = new StringTokenizer(br.readLine().trim());
				for (int c = 0; c < N; c++) {
					board[r][c] = Integer.parseInt(st.nextToken());
				}
			}
			
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < N; c++) {
					if (board[r][c] == 0) {
						pinball(r, c);
					}
				}
			}
			
			System.out.printf("#%d %d\n", xx, max);
		}
	}
	
	private static void pinball (int r, int c) {
		
		int dr, dc, cr, cc, count;
		
		for (int d = 0; d < 4; d++) {
			dr = ddr[d];
			dc = ddc[d];
			cr = r;
			cc = c;
			count = 0;
			
			while (true) {
				
				cr += dr;
				cc += dc;
				
				// 벽 부딪힐 때
				if (cr < 0 || cr >= N || cc < 0 || cc >= N) {
					count += 1;
					
					if (dr != 0) dr = -dr;	// 상하 방향 전환
					else dc = -dc;			// 좌우 방향 전환
					
					cr += dr;
					cc += dc;
				}
				
				if (board[cr][cc] == 1) {
					if (dc == -1) {
						dc = 0;
						dr = -1; 
					} else if (dr == 1) {
						dr = 0;
						dc = 1;
					} else {
						dr = -dr;
						dc = -dc;
					}
					
					count += 1;
				} else if (board[cr][cc] == 2) {
					if (dc == -1) {
						dc = 0;
						dr = 1;
					} else if (dr == -1) {
						dr = 0;
						dc = 1;
					} else {
						dr = -dr;
						dc = -dc;
					}
					
					count += 1;
				} else if (board[cr][cc] == 3) {
					if (dc == 1) {
						dc = 0;
						dr = 1;
					} else if (dr == -1) {
						dr = 0;
						dc = -1;
					} else {
						dr = -dr;
						dc = -dc;
					}
					
					count += 1;
				} else if (board[cr][cc] == 4) {
					if (dc == 1) {
						dc = 0;
						dr = -1;
					} else if (dr == 1) {
						dr = 0;
						dc = -1;
					} else {
						dr = -dr;
						dc = -dc;
					}
					
					count += 1;
				} else if (board[cr][cc] == 5) {
					dr = -dr;
					dc = -dc;
					
					count += 1;
				}

				if (cr == r && cc == c) break;
				if (board[cr][cc] == -1) break;
				
				if (board[cr][cc] >= 6 && board[cr][cc] <= 10) {
					int[] rc = wormhall(cr, cc);
					cr = rc[0];
					cc = rc[1];
				}
			}
			
			max = Math.max(max, count);
		}
	}
	
	private static int[] wormhall(int x, int y) {
		int[] rc = new int[2];
		
		Loop:
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (board[r][c] == board[x][y] && (x != r || y != c)) {
					rc[0] = r;
					rc[1] = c;
					break Loop;
				}
			}
		}
		
		return rc;
	}
}
