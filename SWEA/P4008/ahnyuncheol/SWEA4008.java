import java.io.*;
import java.util.*;

// 숫자 만들기
public class SWEA4008 {
	static int[] numbers;
	static int max = Integer.MIN_VALUE;
	static int min = Integer.MAX_VALUE;
	
	static char[] sel;		// 선택 배열
	static boolean[] chk;	// 체크 배열
	static char[] opers;	// 원본 배열(사칙연산)
	
	public static void main(String[] args) throws IOException {

		System.setIn(new FileInputStream("in"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());

		for (int xx = 1; xx <= T; xx++) {

			int N = Integer.parseInt(br.readLine().trim());
			opers = new char[N-1];
			numbers = new int[N];
			max = Integer.MIN_VALUE;
			min = Integer.MAX_VALUE;

			st = new StringTokenizer(br.readLine().trim());
			int idx = 0;
			for (int i = 0; i < 4; i++) {
				int n = Integer.parseInt(st.nextToken());
				
				for (int j = 0; j < n; j++) {
					switch (i) {
					case 0:
						opers[idx++] = '+';
						break;
					case 1:
						opers[idx++] = '-';
						break;
					case 2:
						opers[idx++] = '*';
						break;
					case 3:
						opers[idx++] = '/';
						break;
					default:
						break;
					}
				}
			}

			st = new StringTokenizer(br.readLine().trim());
			for (int i = 0; i < N; i++) {
				numbers[i] = Integer.parseInt(st.nextToken());
			}

			sel = new char[N-1];
			chk = new boolean[N-1]; 
			permutation(0);
			
			System.out.printf("#%d %d\n", xx, max - min);
		}
		
		
	}
	
	// 순열
	public static void permutation(int idx) {

		if (idx == opers.length) {
			calc(sel);
			return;
		}
		
		char dup = ' ';
		for (int i = 0; i < opers.length; i++) {
			
			if (chk[i] == false) {
				if (dup == opers[i]) continue;	// 중복 제거를 위한 코드
				chk[i] = true;
				sel[idx] = opers[i];
				permutation(idx + 1);
				chk[i] = false;
				dup = opers[i];
			}
		}
	}

	// 계산하는 메소드
	public static void calc(char[] sel) {
		int answer = numbers[0];
		
		for (int i = 0; i < sel.length; i++) {
			switch (sel[i]) {
			case '+':
				answer += numbers[i+1];
				break;
			
			case '-':
				answer -= numbers[i+1];
				break;
			
			case '*':
				answer *= numbers[i+1];
				break;
			
			case'/':
				answer /= numbers[i+1];
				break;
			default:
				break;
			}
		}
		
		max = Math.max(max, answer);
		min = Math.min(min, answer);
	}
}
