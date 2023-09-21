import java.io.*;
import java.util.*;

/*
 * union find로 해결
 */
public class SWEA7465 {
	static int[] parents;
	static int N;
	
	// 서로소 집합 만들기
	static void make() {
		parents = new int[N];
		for (int i = 0; i < N; i++) {
			parents[i] = i;
		}
	}
	
	// x의 root노드 찾기
	static int find(int x) {
		if (x == parents[x]) {
			return x;
		}
		
		return parents[x] = find(parents[x]);
	}
	
	// x의 집합과 y의 집합 합치기
	static boolean union(int x, int y) {
		int xRoot = find(x);	// x의 root 노드
		int yRoot = find(y);	// y의 root 노드
		
		// 같은 집합이면 return false
		if (xRoot == yRoot) return false;
		
		parents[yRoot] = xRoot;
		return true;
	}
	
	public static void main(String[] args) throws IOException {
		
		System.setIn(new FileInputStream("in"));
		java.util.Scanner sc = new java.util.Scanner(new InputStreamReader(System.in));
		
		int T = sc.nextInt();
		
		for (int xx = 1; xx <= T; xx++) {
			
			N = sc.nextInt();
			int M = sc.nextInt();
			
			boolean[] chk = new boolean[N];	// 집합의 수를 구하기위한 배열 각 인덱스는 root노드를 의미
			int cnt = 0;	// 집합의 수
			
			make();
			
			// 입력받은 관계 union
			for (int i = 0; i < M; i++) {
				
				int x = sc.nextInt() - 1;
				int y = sc.nextInt() - 1;
				
				union(x, y);
			}
			
			// 집합의 수 구하기
			chk = new boolean[N];
			for (int i = 0; i < N; i++) {
				if (chk[find(i)] == false) {
					chk[find(i)] = true;
					cnt += 1;
				}
			}
			
			System.out.printf("#%d %d\n", xx, cnt);
		}
	}

}
