import java.io.*;
import java.util.*;

public class BJ16234 {
	static int N;
	static int[][] board;
	static boolean[][] chk;
	static int L;
	static int R;
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("in"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		board = new int[N][N];
		chk = new boolean[N][N];
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < N; c++) {
				board[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		peopleMove();
		// printBoard();
	}
	
	private static void peopleMove() {
		// BFS
		Queue<int[]> q = new LinkedList<>();
		Queue<int[]> avgbox = new LinkedList<>();
		int cnt = 0, sum = 0, avg = 0, cr, cc;
		int[] point = new int[2];
		int[] dr = {0, 0, 1, -1};
		int[] dc = {1, -1, 0, 0};
		
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				
				if (chk[r][c] == false) {
					System.out.println(r + " " + c);
					// BFS
					chk[r][c] = true;
					q.add(new int[] {r, c});
					avgbox.add(new int[] {r, c});
					sum += board[r][c];
					cnt += 1;
					
					while (!q.isEmpty()) {
						point = q.poll();
						chk[point[0]][point[1]] = true;

						
						printChk();
						printBoard();
						for (int i = 0; i < 4; i++) {
							cr = point[0] + dr[i];
							cc = point[1] + dc[i];
							
							if (cr >= 0 && cr < N && cc >= 0 && cc < N) {
								if (chk[cr][cc] == false 
										&& (Math.abs(board[r][c] - board[cr][cc]) >= L
										&& Math.abs(board[r][c] - board[cr][cc]) <= R)) {
									
									// chk[cr][cc] = true;
									q.add(new int[] {cr, cc});
									avgbox.add(new int[] {cr, cc});
									sum += board[cr][cc];
									cnt += 1;
								}
							}
						}
					}
					
					//System.out.println(cnt);
					avg = (int)(sum / cnt);
					while (!avgbox.isEmpty()) {
						point = avgbox.poll();
						board[point[0]][point[1]] = avg;
					}
					
					printBoard();
				}
			}
		}
	}
	
	private static boolean isEquals(int[][] before, int[][] after) {
		
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (before[r][c] != after[r][c]) return false; 
			}
		}
		
		return true;
	}
	
	private static void printBoard() {
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				System.out.print(board[r][c] + " ");
			}
			System.out.println();
		}
	}
	
	private static void printChk() {
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				System.out.print(chk[r][c] + " ");
			}
			System.out.println();
		}
	}
}
