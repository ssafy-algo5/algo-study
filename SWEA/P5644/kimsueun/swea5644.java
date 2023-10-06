import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

class swea5644 {
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	static int t, m, a, amove[], bmove[], charge[], ans;
	static boolean bc[][][]; // (x,y)인데 n번째 bc의 처리량이 저장됨

	static int dx[] = { 0, -1, 0, 1, 0 };
	static int dy[] = { 0, 0, 1, 0, -1 };

	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		t = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= t; tc++) {
			st = new StringTokenizer(br.readLine());
			m = Integer.parseInt(st.nextToken());
			a = Integer.parseInt(st.nextToken());

			// 초기화
			amove = new int[m]; // 0-indexed
			bmove = new int[m]; // 0-indexed
			charge = new int[a + 1]; // 1-indexed
			bc = new boolean[a + 1][11][11]; // 1-indexed
			ans = 0;

			// a 이동 정보 저장
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < m; i++) {
				amove[i] = Integer.parseInt(st.nextToken());
			}

			// b 이동 정보 저장
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < m; i++) {
				bmove[i] = Integer.parseInt(st.nextToken());
			}

			// ap의 정보 저장
			for (int i = 1; i <= a; i++) {
				st = new StringTokenizer(br.readLine());
				int y = Integer.parseInt(st.nextToken());
				int x = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				int p = Integer.parseInt(st.nextToken());

				// ap의 p값 = 처리량을 저장함
				charge[i] = p;

				// (x, y)부터 시작해서 c번 leveling 하여 bc의 영역임을 저장함
				Queue<int[]> q = new ArrayDeque<int[]>();
				boolean[][] visited = new boolean[11][11]; // 1-indexed

				visited[x][y] = true;
				bc[i][x][y] = true;
				q.add(new int[] { x, y });

				for (int j = 0; j < c; j++) { // c번 bfs를 돌릴 것임
					int size = q.size();

					for (int k = 0; k < size; k++) {
						int[] cur = q.poll();
						int curX = cur[0];
						int curY = cur[1];

						for (int dir = 1; dir <= 4; dir++) {
							int nx = curX + dx[dir];
							int ny = curY + dy[dir];

							if (nx < 1 || nx > 10 || ny < 1 || ny > 10)
								continue;
							if (visited[nx][ny])
								continue;

							visited[nx][ny] = true;
							bc[i][nx][ny] = true;
							q.add(new int[] { nx, ny });
						}
					}
				}
			}

			// print();

			move();
			
            sb = new StringBuilder();
            sb.append("#"+tc+" " +ans);
			System.out.println(sb);
		}
	}

	private static void move() {
		//m번만큼 이동하면서 사람을 이동시킴 
		int ax = 1, ay = 1;
		int bx = 10, by = 10;
		calcCharge(ax,ay,bx,by); //시작점도 충전이 가능한지 꼭 체크해야함
		
		for (int i = 0; i < m; i++) {
			//a가 이동한 후 좌표
			ax += dx[amove[i]];
			ay += dy[amove[i]];
			
			//b가 이동한 후 좌표
			bx += dx[bmove[i]];
			by += dy[bmove[i]];
			
//			System.out.println("a : "+ax+" "+ay);
//			System.out.println("b : "+bx+" "+by);
			
			calcCharge(ax,ay,bx,by);
			
		}
	}

	private static void calcCharge(int ax, int ay, int bx, int by) {
		//a, b가 충전가능한 bc를 찾음 
		List<Integer> listA = new ArrayList<Integer>();
		List<Integer> listB = new ArrayList<Integer>();
		//System.out.println(listA.size() + " * "+listB.size());
		
		int max=0, tmp=0;
		
		for (int j = 1; j <= a; j++) {
			if(bc[j][ax][ay]) listA.add(j);
			if(bc[j][bx][by]) listB.add(j);
		}
//		for(int idx : listA) {
//			System.out.println("lista idx : "+idx);
//		}
//		for(int idx : listB) {
//			System.out.println("listb idx : "+idx);
//		}
		
		if(listA.size()>0 && listB.size()>0) {
			//a와 b의 중복조합으로 최댓값을 구함 
			for(int idx1 : listA) {
				for(int idx2 : listB) {
					tmp = 0;
					
					if(idx1==idx2) {
						tmp = charge[idx1];
					}
					
					else {
						tmp += charge[idx1];
						tmp += charge[idx2];
					}
					
					max = Math.max(max, tmp);
				}
			}
		}
		else if(listA.size()>0) {
			//a가 충전할 수 있는 bc 중 가장 큰 충젼량을 저장 
			for(int idx : listA) {
				if(max < charge[idx]) max = charge[idx];
			}
		}
		else if(listB.size()>0) {
			//b가 충전할 수 있는 bc 중 가장 큰 충젼량을 저장 
			for(int idx : listB) {
				if(max < charge[idx]) max = charge[idx];
			}
		}
		
		ans+=max;
	}

	private static void print() {
		// bc 출력해보기
		for (int k = 1; k <= a; k++) {
			for (int i = 1; i <= 10; i++) {
				for (int j = 1; j <= 10; j++) {
					System.out.print(bc[k][i][j] + "\t");
				}
				System.out.println();
			}
			System.out.println();
		}
	}
}