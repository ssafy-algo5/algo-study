import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 각 맵 1*1 자리에는 모두 나라가 존재하고 n 명이 살고있다 !
 * 인접한 나라에는 국경선이 존재 모든 국경은 정사각형 형태이다 
 * 
 * (지역 배열이 체크 배열의 역할도 가능할거 같다) -> 안됬다 dis는 계속해서 갱신 되기 때문이다 dis 를 사용하지 않으면 매 연합의 각 나라가 BFS에 들어간다 !
 * ->체크배열 하나 더 만들어서 해결했다 !
 * 
 * 인구이동 
 * 1. 국경선을 공유하는 두 나라의 인구 차이가 L명 이상 R명 이하라면 국경선을 연다 (완전 탐색 필요) -> check() 메소드 !
 * for 문으로 4방을 확인해서 하나의 국경선이라도 열리면 BFS 호출 ! -> BFS() 메소드 !
 * 
 * 2. 인구를 이동시킨다 (평균은 BFS 하나의 연합을 구할때 계산해둔다) -> change() 메소드 !
 * 같은 지역에 속해있는 인구들을 모두 이동시킨다 
 * 
 * 3. 연합을 해제하고 모든 국경선이 닫힌다.
 * 
 * 새로 정의된 각 나라의 인구로 또 인구이동을 시작한다
 * 위와 같은 한 사이클의 로직이 하루이다 ! !
 * 
 * 연합이 하나도 나오지 않는다면 끝낸다 
 */
public class 인구이동 {
	static class Point {
		int x, y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	static int dx[] = { -1, 0, 1, 0 }; // 상 우 하 좌
	static int dy[] = { 0, 1, 0, -1 };
	static int N, L, R, map[][], dis[][], value[];
	static boolean v[][];
	static Queue<Point> Q = new LinkedList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken()); // N*N
		L = Integer.parseInt(st.nextToken()); // 최소
		R = Integer.parseInt(st.nextToken()); // 최대

		map = new int[N][N];
		// 매일마다 가능한 연합의 숫자를 지정해서 계속 갱신해준다 ! 
		dis = new int[N][N];
		// 해당 연합의 평균값을 지정해둔다 !
		value = new int[2000000];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int answer = 0;
		
		while(true) {
			// 해당 일자의 체크 배열을 만든다 !
			v = new boolean[N][N];
			
			int start = name;

			// 각 자리를 확인한다
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
//					if (dis[i][j] != 0)
//						continue;
					
					if (v[i][j])
						continue;
					
					if (check(i, j)) {
						BFS(i, j);
					}
				}
			}
			int end = name;
			// 더이상의 연합이 발생하지 않는다 !
			if (start == end)
				break;

			chage();
			answer++;
		}

		System.out.println(answer);
	}

	// 만들어진 연합을 처리한다
	private static void chage() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(dis[i][j] == 0) continue;
				map[i][j] = value[dis[i][j]];
			}
		}
		//print(map);
	}

	// 한 지역을 만든다
	static int name = 0;
	private static void BFS(int i, int j) {
		// TODO Auto-generated method stub
		name++;

		int sum = map[i][j];
		Q.add(new Point(i, j));
		dis[i][j] = name;
		v[i][j] = true;
		// 한 연합의 갯수 !
		int cnt = 1;

		while (!Q.isEmpty()) {
			Point p = Q.poll();

			for (int k = 0; k < 4; k++) {
				int nx = p.x + dx[k];
				int ny = p.y + dy[k];

				if (nx >= 0 && nx < N && ny >= 0 && ny < N && dis[nx][ny] != name) {
					int sub = Math.abs(map[p.x][p.y] - map[nx][ny]);
					if (sub >= L && sub <= R) {
						Q.add(new Point(nx, ny));
						dis[nx][ny] = name;
						v[nx][ny] = true;
						sum += map[nx][ny];
						cnt++;
					}
				}
			}
		}
		// 해당 연합이름의 평균 인구수를 저장해둔다 !
		value[name] = sum / cnt;

	}

	// 4 방향중 탐색이 가능한 곳이 있는지 확인한다
	private static boolean check(int x, int y) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			if (nx < 0 || nx >= N || ny < 0 || ny >= N)
				continue;

			int sub = Math.abs(map[x][y] - map[nx][ny]);
			if (sub >= L && sub <= R) {
				return true;
			}
		}

		return false;
	}
	
	private static void print(int[][] map) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("---------------");
		
	}
}

/*
 * 이 문제의 요점은 연결다리가 열렸다가 다시 닫히고 그렇기 때문에 다음 날에는 다시 완전탐색이 필요한 문제였다 
 * 이미 평균이 적용되었던 자리도 다음날에 연결되지 않았던 나라와 연결이 될수 있기때문에 계속적인 완전탐색이 필요했다 !
 * 시뮬레이션 적인 문제이기 때문에 지문에서 요구한 순서대로 그대로 구현하는게 중요했다 !
 * 
 * dis 는 계속 갱신되기 때문에 체크 배열로 같이 사용하기에 무리가 있었다 그래서
 * 매 하루마다 체크배열을 사용해서 메모리와 시간을 훨신 단축할수 있었다 ! 
 * 매일 생성되는 연합에 나라들마다 들어가서 확인해야 하는 문제점을 없앴다 !
 */