import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 0 과 1 로 공간과 치즈의 위치가 주어진다 
 * 이때 1 이라는 치즈는 외부 공기에 2면 이상 닿아있으면 썪을수있다 !
 * 
 * 맨 가장 자리에는 치즈가 존재하지 않고 만약 옆에 공백은 있지만 그게 빈 공백인지 아닌지 구분이 중요한 문제이다
 * 이 부분은 해당 공백을 체크해주는게 관건이다 !
 * 
 */
public class 치즈 {
	static class Point {
		int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	static int dx[] = {-1, 0, 1, 0};
	static int dy[] = {0, 1, 0, -1};
	static int N, M, map[][], answer = 0;
	static boolean v[][];
	static Queue<Point> Q = new LinkedList<>();
	static ArrayList<Point> ch = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken()); // 가로
		M = Integer.parseInt(st.nextToken()); // 세로
		map = new int[N][M];
		v= new boolean[N][M];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				// 치즈를 Arr에 담아둔다 !
				if (map[i][j] == 1) {
					ch.add(new Point(i, j));
				}
			}
		}

		// 0,0 에서 시작해서 외부 공기를 2로 체크한다 !
		Q.add(new Point(0, 0));
		v[0][0] = true;
		while (!ch.isEmpty()) {
			answer++;
			// 빈 공간을 확보한다 !
			BFS();
			// 썩는 치즈를 확인한다 ( ch 에서 지우고 Q 에 추가한다 ! )
			delete();
			//print(map);
		}
		// 해당 치즈를 제거하고 제거한 치즈로부터 0인 값들은 또 2로 바꾼다 !
		
		System.out.println(answer);

	}

	private static void delete() {
		// TODO Auto-generated method stub
		for (int i = 0; i < ch.size(); i++) {
			Point p = ch.get(i);
			
			int cnt = 0;
			for (int j = 0; j < dx.length; j++) {
				int nx = p.x + dx[j];
				int ny = p.y + dy[j];
				if(nx >= 0 && nx < N && ny >= 0 && ny < M &&  map[nx][ny] == 2) {
					cnt++;
				}
				// 녹는 치즈 !
			}
			if(cnt >= 2) {
				Q.add(new Point(p.x, p.y));
				ch.remove(i);
				i--;
			}
		}
	}

	private static void print(int[][] map) {
		// TODO Auto-generated method stub
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}

	private static void BFS() {
		// TODO Auto-generated method stub
		while (!Q.isEmpty()) {
			Point p = Q.poll();
			map[p.x][p.y] = 2;
			
			for (int i = 0; i < dx.length; i++) {
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];
				if(nx >= 0 && nx < N && ny >= 0 && ny < M && !v[nx][ny] && map[nx][ny] == 0) {
					Q.add(new Point(nx,ny));
					v[nx][ny] = true;
				}
			}
			
		}
		
	}
}











