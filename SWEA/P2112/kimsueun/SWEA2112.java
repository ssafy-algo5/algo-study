package Study.day0921;

/*
 * 필름의 두께 D는 각각 약품을 A를 투입하거나 B를 투입하지 않거나 3가지 경우 중 하나임.
 * 그래서 각 막에서의 3가지 경우를 모두 살펴보면 됨.
 * 약을 투입할 때마다 film값 자체를 바꿔준다면 시간초과가 날 것임.
 * film값을 바꿔주기 보다는 각 층이 3가지 상황 중 어떤 상황인지를 저장해주기로 하자.
 * 그렇게 모든 층의 경우를 결정해줬다면 결정된 상황에서 합격기준에 부합하는지를 알아봄.
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA2112 {
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	static int t, d, w, k, ans;
	static int[][] film;
	static int[] medicine;
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		
		t = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= t; tc++) {
			input(); //인풋 입력받기 및 초기화
			
			solve(); //문제를 풀고 ans을 구함
			
			sb.append("#"+tc+" "+ans);
			System.out.println(sb);
		}
	}

	private static void input() throws IOException {
		sb = new StringBuilder();
		
		ans = Integer.MAX_VALUE;
				
		st = new StringTokenizer(br.readLine());
		d = Integer.parseInt(st.nextToken());
		w = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		//초기화
		film = new int[d][w];
		medicine = new int[d];
		
		for (int i = 0; i < d; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < w; j++) {
				film[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}
	private static void solve() {
		recursive(0,0); //0번째 줄부터 d개의 줄에 대해서 약을 투여할 건지 말건지 결정함
	}

	//medicine배열 -> -1:아무것도 투여안함, 0 : A를 투여함, 1 :B를 투여함
	private static void recursive(int cntD, int cntMedicine) {
		// TODO Auto-generated method stub
		if(cntD==d) { //모든 층의 상황을 모두 결정한 경우
			//ans보다 cntMedicine이 작은 경우만 합격기준에 맞는지를 확인함
			if(cntMedicine < ans) {
				if(check()) ans = cntMedicine;
			}
			
			return;
		}
		
		for(int i=-1; i<2; i++) {
			medicine[cntD] = i; //cntD번째 줄에 i번 상황을 부여함
			
			if(i==-1) recursive(cntD+1, cntMedicine);
			else recursive(cntD+1, cntMedicine+1);
		}
	}

	private static boolean check() {
		//현재 medicine의 상황대로 각 열을 확인해보고 합격기준인 k이상이 모두 된다면 1리턴
		
		for (int j = 0; j < w; j++) { //열을 기준으로 봄 
			int cnt=1; //연속되는 횟수 카운팅 
			
			for(int i = 0; i < d-1; i++) { //행, i번째에서 다음 거까지 함께 확인하기 때문에 d-1까지
				//지금거랑 다음거를 비교해서 같아야 for문을 계속 이어감
				//medicine의값이 -1이라면 약 투여가 되지 않은 것
				int curM = medicine[i]==-1? film[i][j] : medicine[i]; 
				int nextM = medicine[i+1]==-1? film[i+1][j] : medicine[i+1];
				
				if(curM!=nextM) cnt=1; //개수를 처음부터 다시 세야하므로 1로 초기화
				
				else{
					cnt++;
					if(cnt>=k) break; //합격기준 k에 부합하는 열임, 다음 열을 보러 감 
				}
			}
			//한 열에 대해 모든 행을 보았고 최종 구해진 cnt가 k보다 작은지 판단하기
			if(cnt<k) return false;
		}
		
		return true;
	}
}

