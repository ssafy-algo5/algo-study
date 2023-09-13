/*
 * 구현방법
 * 재귀를 돌면서 인구합과 나라의 갯수를 구하면서 시뮬레이션 했습니다.
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int N,L,R;
	static int[][] map;
	static int[][] v;
	static boolean flags;
	static int days = -1;
	static int[][] dm = {{0, 1},{0, -1},{1, 0},{-1, 0}};
	static int[] sum, num;
	static int union;

	public static void main(String[] args) throws IOException {
		//System.setIn(new FileInputStream("./input/인구이동"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		// L이상 R이하
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		flags = true;
		do {
			days++;
			flags = false;
			union = 0;
			sum = new int[N*N + 1];
			num = new int[N*N + 1];
			v = new int[N][N];
			
			for (int row = 0; row < N; row++) {
				for (int col = 0; col < N; col++) {
					// 방문한적이 없다면
					if (v[row][col] == 0) {
						union++;
						recursive(row, col);
					}
				}
			}

			for (int r = 0; r < N; r++) {
				for (int c = 0; c < N; c++) {
					if (v[r][c] > 0) {
						map[r][c] = sum[v[r][c]] / num[v[r][c]];
					}
				}
			}
		} while (flags);
		System.out.println(days);

	}

	private static void recursive(int row, int col) {
		v[row][col] = union;
		sum[union] += map[row][col];
		num[union] += 1;

		for (int dr = 0; dr < 4; dr++) {
			if (row + dm[dr][0] < N && row + dm[dr][0] >= 0
			  &&col + dm[dr][1] < N && col + dm[dr][1] >= 0) {
				if (v[row + dm[dr][0]][col + dm[dr][1]] == 0
				  && L <= Math.abs(map[row][col] - map[row + dm[dr][0]][col + dm[dr][1]])
				  && R >= Math.abs(map[row][col] - map[row + dm[dr][0]][col + dm[dr][1]])) {
					flags = true;
					recursive(row + dm[dr][0], col + dm[dr][1]);
				}
			}
		}

	}

}
