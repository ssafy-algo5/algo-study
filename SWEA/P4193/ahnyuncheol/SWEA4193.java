import java.util.*;
import java.io.*;

// 수영대회 결승전
public class SWEA4193 {

	public static void main(String[] args) throws IOException {
		// bfs Queue
		
		System.setIn(new FileInputStream("in"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for (int xx = 1; xx <= T; xx++) {
			
			int N = Integer.parseInt(br.readLine());
			int[][] sea = new int[N][N];	// 바다
			int[] from = new int[2];		// 출발점
			int[] to = new int[2];			// 도착점
			
			// 바다 구성하기
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					sea[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			st = new StringTokenizer(br.readLine());
			from[0] = Integer.parseInt(st.nextToken());
			from[1] = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(br.readLine());
			to[0] = Integer.parseInt(st.nextToken());
			to[1] = Integer.parseInt(st.nextToken());
			
			// bfs
			int ans = bfs(sea, from, to);
			System.out.printf("#%d %d\n", xx, ans);
		}
	}
	
	private static int bfs(int[][] board, int[] from, int[] to) {
		
		int answer = 0;
		int N = board.length;
		int[] dr = {0, 0, 1, -1};
		int[] dc = {1, -1, 0, 0};
		
		Queue<int[]> queue = new LinkedList<>();
		List<int[]> list = new ArrayList<>();
		queue.add(from);
		board[from[0]][from[1]] = 9;	// 9 -> 방문처리
		
		int cr, cc, mr, mc;
		
		while (true) {
			
			// 변화가 없으면
			if (queue.isEmpty()) {
				return -1;
			}
			
			// queue -> list
			while (!queue.isEmpty()) {
				list.add(queue.poll());
			}
			
			// list를 돌면서 방문처리하고, queue에 다음에 탐색할 값 저장
			for (int[] p: list) {
				cr = p[0];
				cc = p[1];
				for (int i = 0; i < 4; i++) {
					mr = cr + dr[i];
					mc = cc + dc[i];
					
					// 소용돌이 있을 때
					if (answer % 3 != 2) {
						if (mr >= 0 && mr < N && mc >= 0 && mc < N) {
							if (board[mr][mc] == 0) {
								
								queue.add(new int[] {mr, mc});
								board[mr][mc] = 9;
							}
							if (board[mr][mc] == 2) {
								queue.add(new int[] {cr, cc});
							}
						}
					} 
					// 소용돌이 없을 때
					else {
						
						if (mr >= 0 && mr < N && mc >= 0 && mc < N) {
							if (board[mr][mc] == 0) {
								queue.add(new int[] {mr, mc});
								board[mr][mc] = 9;
							}
							if (board[mr][mc] == 2) {
								queue.add(new int[] {mr, mc});
							}
						}
					} 
				}
			}
			
			list.clear();
			answer += 1;
			
			int distR = to[0];
			int distC = to[1];
			if (board[distR][distC] == 9) break;
		}
		
		return answer;
	}
	
	private static void print(int[][] board) {
		int N = board.length;
		
		for (int r = 0; r < N; r++) {
			for (int c= 0; c < N; c++) {
				System.out.print(board[r][c] +" ");
			}
			System.out.println();
		}
	}
}
