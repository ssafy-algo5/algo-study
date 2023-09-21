import java.io.*;
import java.util.*;

/*
 * union find�� �ذ�
 */
public class SWEA7465 {
	static int[] parents;
	static int N;
	
	// ���μ� ���� �����
	static void make() {
		parents = new int[N];
		for (int i = 0; i < N; i++) {
			parents[i] = i;
		}
	}
	
	// x�� root��� ã��
	static int find(int x) {
		if (x == parents[x]) {
			return x;
		}
		
		return parents[x] = find(parents[x]);
	}
	
	// x�� ���հ� y�� ���� ��ġ��
	static boolean union(int x, int y) {
		int xRoot = find(x);	// x�� root ���
		int yRoot = find(y);	// y�� root ���
		
		// ���� �����̸� return false
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
			
			boolean[] chk = new boolean[N];	// ������ ���� ���ϱ����� �迭 �� �ε����� root��带 �ǹ�
			int cnt = 0;	// ������ ��
			
			make();
			
			// �Է¹��� ���� union
			for (int i = 0; i < M; i++) {
				
				int x = sc.nextInt() - 1;
				int y = sc.nextInt() - 1;
				
				union(x, y);
			}
			
			// ������ �� ���ϱ�
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
