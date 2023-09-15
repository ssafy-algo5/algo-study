import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

public class 야구 {
	static boolean[] v;
	static int[] sel = new int[9]; // 각 순열에서의 타석을 보유한다 ! 한번 정해지면 한 게임이 끝날때까지 유지한다 !
	static int[][] arr;
	static int N, answer = 0;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine()); // 이닝의 횟수 !

		arr = new int[N][9]; // N개의 이닝의 각 타자의 정보가 담겨있다 !
		v = new boolean[9]; // 9개의 선택을 받는다 !

		// 각 이닝의 정보를 입력으로 받는다 !
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 9; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 1 안타 | 2 2루타 | 3 3루타 | 4 홈런 | 0 아웃 !
		// 각 이닝의 0번째 타석은 3번 타석으로 고정하고 나머지 타석들을 줄을 세워야 한다 !
		// ㅁ ㅁ ㅁ ㅇ ㅁ ㅁ ㅁ ㅁ ㅁ 네모에 8! 으로 순열이다 !
		
		//틀렸던 부분 
//		v[3] = true;
//		sel[3] = 3;
		
		// 4번 타자 고정 !
		v[0] = true;
		sel[3] = 0;

		DFS(0); // 0 ~ 8번의 타석을 정한뒤 말단에서 점수를 계산하는 방식이다 !
		
		System.out.println(answer);
	}

	private static void DFS(int idx) {
		// TODO Auto-generated method stub
		// 여기서는 이미 타석이 고정되어있다 !
		if (idx == 9) {
			//System.out.println(Arrays.toString(sel));
			int score = play();

			answer = Math.max(answer, score);
			return;
		}
		
		if (idx == 3) {// 3번 타자는 고정 !
			DFS(idx + 1);
			return;
		}
		
		// 타석을 순열로 만든다 !
		for (int i = 0; i < 9; i++) {
			if (v[i] == true) // 중복을 제거한다 !
				continue;

			else { // 나머지 타순은 순열 !
				sel[idx] = i; // 셀 배열은 계속해서 덮여 쓰여진다 !
				v[i] = true;
				DFS(idx + 1);
				v[i] = false;
			}
		}
	}

	// 이닝 만큼의 플레이를 진행한다 !
	// 해당 배열을 계속해서 돌면서 ! 이닝별로 타석은 그대로지만 각 선수의 타율이 바뀌기에 전환 필요 !
	
	private static int play() {
		// TODO Auto-generated method stub
		int inning = 0;
		int out = 0;
		int score = 0;
		int position = 0;
		int[] cur = new int[4]; // 진출 0 1 2 3 루에 상황을 그려놓는다 !

		while (!(inning == N)) { // 모든 이닝이 끝나면 종료한다 !
			if (position == 9) {
				position = 0; // 타석은 원처럼 계속 돌아간다 !
			}

			cur[0] = 1; // 타자가 타석에 들어선다 !
			// 게임을 진행한다 !
			switch (arr[inning][sel[position]]) { // 순열로 구해놓은 주자를 뽑아낸다 !

			case 0: // 주자 아웃 !
				out++;
				cur[0] = 0;
				break;

			case 1: // 주자 1루 진출 !
				score += cur[3];
				cur[3] = cur[2];
				cur[2] = cur[1];
				cur[1] = cur[0];
				
				cur[0] = 0;
				break;
				
			case 2: // 주자 2루 진출 !
				score += cur[3] + cur[2];
				cur[3] = cur[1];
				cur[2] = cur[0];
				
				cur[0] = cur[1] = 0;
				break;
				
			case 3: // 주자 3루 진출 !
				score += cur[3] + cur[2] + cur[1];
				cur[3] = cur[0];
				
				cur[0] = cur[1] = cur[2] = 0;
				break;
				
			case 4: // 주자 홈런 !
				score += cur[3] + cur[2] + cur[1] + cur[0];
				
				cur[0] = cur[1] = cur[2] = cur[3] = 0;
				break;

			}

			position++; // 다음 타석 호출 !

			// 3 아웃이 된다면 1 이닝을 +1 하고 out 카운트는 초기화 !
			if (out == 3) {
				inning++;
				out = 0;
				cur[0] = cur[1] = cur[2] = cur[3] = 0; // 이닝이 끝나면 루의 상황을 초기화 !
			}

		}

		return score;
	}

}
