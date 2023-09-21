package Study.day0921;

/*
 * cnt[] : 연산자의 개수를 저장
 * num[] : 수식에 사용되는 숫자 저장
 * order[] : n-1개의 연산의 순서 저장
 *  + : 0, - : 1, * : 2, / : 3
 *  
 *  연산자가 여러 번 나올 수 있고 (중복허용) 순서가 유의미하므로, 중복을 허용하는 순열을 뽑아줌.
 *  dfs를 이용하여 n-1개의 연산자의 순서를 다 구할 때까지 재귀를 돌려줌.
 *  연산자를 고를 때는 0~3 까지 중 하나의 값을 고를 수 있음.
 *  그러나 가지치기를 해주지 않으면 4^11이 되므로 시간초과가 날 것임.
 *  그래서 dfs를 넘길 때 각 연산자들의 개수를 함께 넘겨줌.
 *  매번 뽑힌 순열의 결과가 유망한지를 판단한 후 유망할 때만 재귀를 이어감.
 *  
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA4008 {
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	static int t, n, cnt[], num[], order[], ansMax, ansMin;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		
		t = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= t; tc++) {
			input(); //인풋 입력받기 및 초기화
			
			recursive(0,0,0,0,0); //문제를 풀고 ans을 구함
			
			sb.append("#"+tc+" "+(ansMax-ansMin));
			System.out.println(sb);
			
		}
	}

	private static void input() throws IOException {
		sb = new StringBuilder();
				
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		
		ansMax = Integer.MIN_VALUE;
		ansMin = Integer.MAX_VALUE;
		
		//초기화
		cnt = new int[4]; //'+', '-', '*', '/' 순서대로 개수 저장 
		num = new int[n]; //숫자 저장 
		order = new int[n-1]; //연산자의 순서 저장 
		
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < 4; i++) {
			cnt[i] = Integer.parseInt(st.nextToken());
		}
		
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			num[i] = Integer.parseInt(st.nextToken());
		}
	}

	private static void recursive(int cntIdx, int cntA, int cntB, int cntC, int cntD) {
		if(cntIdx==n-1) {
			//연산자의 개수 일치하는지 확인하기
			if(cnt[0]!=cntA) return;
			if(cnt[1]!=cntB) return;
			if(cnt[2]!=cntC) return;
			if(cnt[3]!=cntD) return;
			
			//이제 이 연산자들의 순서대로 연산을 해주고 가장 큰 값을 찾으면 됨 
			int result = calc();
			if(result < ansMin) ansMin = result;
			if(result > ansMax) ansMax = result;
			
			return;
		}
		
		//중복을 포함한 순열을 구함
		//순열을 구하는 과정에 num에 저장된 연산자의 개수보다 
		//현재까지 구한 연산자의 개수가 크다면 답이 될 가능성이 없으므로 가지치기해줌
		for(int i=0; i<4; i++) { //뽑을 수 있는 연산자는 0~3임
			//연산자의 개수가 이미 가능성이 없다면 끝냄
			if(cnt[0]<cntA || cnt[1]<cntB || cnt[2]<cntC || cnt[3]<cntD) return;
			if(cnt[0]==0 && cntA >0) return;
			if(cnt[1]==0 && cntB >0) return;
			if(cnt[2]==0 && cntC >0) return;
			if(cnt[3]==0 && cntD >0) return;
			
			order[cntIdx] = i;
			if(i==0) recursive(cntIdx+1, cntA+1, cntB, cntC, cntD);
			else if(i==1) recursive(cntIdx+1, cntA, cntB+1, cntC, cntD);
			else if(i==2) recursive(cntIdx+1, cntA, cntB, cntC+1, cntD);
			else if(i==3) recursive(cntIdx+1, cntA, cntB, cntC, cntD+1);
		}
	}

	private static int calc() {
		int result = num[0];
		
		for (int i = 0; i < n-1; i++) {
			int op = order[i];
			if(op==0) { //더해줌
				result += num[i+1];
			}
			else if(op==1) {
				result -= num[i+1];
			}
			else if(op==2) {
				result *= num[i+1];
			}
			else {
				result /= num[i+1];
				
			}
		}
		return result;
	}
	
}
