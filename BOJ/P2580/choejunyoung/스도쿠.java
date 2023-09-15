import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class 스도쿠 {
	static int[][] map;
	static int times;
	static ArrayList<Point> arr = new ArrayList<>();
	static StringBuilder sb = new StringBuilder();

	static class Point {
		int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		map = new int[9][9];

		for (int i = 0; i < 9; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 9; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				// 채워야 하는 공백을 담아둔다 !
				if (map[i][j] == 0) {
					arr.add(new Point(i, j));
				}
			}
		}

		times = arr.size();

		DFS(0);

		print(map);
		System.out.println(sb);

	}

	static boolean flag = false;

	private static void DFS(int idx) {
		if (times == idx) {
			// 말단에 왔다는건 각 공백의 모든 자리가 가능하다는것이고 작은 수부터 대입했으니 정답이 된다 !
			// 한번 말단에 정답이 나왔다는것 ! 이것이 바로 백트레킹 !
			flag = true;
			return;
		}

		// 작은 수부터 넣어서 가능하다면 다음 공백으로 간다 !
		for (int i = 1; i <= 9; i++) {

			map[arr.get(idx).x][arr.get(idx).y] = i;

			if (posible(idx)) {
				DFS(idx + 1);
			}
			// 백트레킹 ! | 여기다 두면 for가 하나라도 덜 들어간다 !
			if (flag)
				return;

			// 이거를 못적었었다 ! -> 들어갔다가 안되는 경우도 생긴다 !
			map[arr.get(idx).x][arr.get(idx).y] = 0;

		}

	}

	private static boolean posible(int idx) {
		// TODO Auto-generated method stub
		Point p = arr.get(idx);
		int x = p.x;
		int y = p.y;

		// 행 검사 !
		for (int i = 0; i < 9; i++) {
			// 해당 빈 자리와 같은 자리는 건너뛴다 !
			if (i == x)
				continue;
			if (map[x][y] == map[i][y])
				return false;
		}

		// 열 검사 !
		for (int i = 0; i < 9; i++) {
			// 해당 빈 자리와 같은 자리는 건너뛴다 !
			if (i == y)
				continue;
			if (map[x][y] == map[x][i])
				return false;
		}

		// 3*3 검사 !
		// 어느 블록을 검사하는지 찾는다 !
		int start_x = (x / 3) * 3;
		int start_y = (y / 3) * 3;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (x == start_x + i && y == start_y + j)
					continue;
				if (map[x][y] == map[start_x + i][start_y + j])
					return false;
			}
		}
		// 위의 3가지 검사에 만족한다면 가능한것이다 !
		return true;
	}

	private static void print(int[][] map) {
		// TODO Auto-generated method stub
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				sb.append(map[i][j] + " ");
			}
			sb.append("\n");
		}
	}

}
/*
 * 단순하게 생각하면 00000 11111 2222 다 다대입해봐야 하지만 flat 하게 생각해보면 앞에 있는 빈칸부터 ! 각 행과 각 열과
 * 3*3 에 겹치는 숫자가 하나도 없다면 ! 그건 정답이 될수 있다는것! 모든 원소의 합이 45가 될수 있다는 것이다 ! 백트레킹 쭉
 * 들어가면서 가지치기를 계속하는 원리이다 ! 백트레킹 문제도 꼭 한 방향으로만 간다는 보장은 없다 ! 즉 체크 해제나 값이 계속해서
 * 쓰이는거를 방지해야 한다는 생각을 갖자 !
 *
 * 완탐의 방식이지만 아예 모든 숫자를 다 배정해놓고 검사를 하는것보다 ! 
 * 하나씩 대입해보며 쭉 나아가는것이 훨신 효율적이다는 생각이 필요했던 문제이다 !
 */