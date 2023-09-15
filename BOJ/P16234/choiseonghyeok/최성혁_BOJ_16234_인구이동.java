import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * BFS로 구역이 얼마나 나눠지는지 찾고 계산한다
 * 구역이 안만들어질때까지 계속 반복한다.
 * 이때, 구역이 여러개 만들어질 수 있으니 해당 좌표 리스트에 담아서 따로 계산
 */
public class 최성혁_BOJ_16234_인구이동 {
	
	static Queue<Country> q =  new LinkedList();
	static int N;
	static int L;
	static int R;
	static int[][] field;
	static boolean[][]check;
	static int DistrictCnt=0;
	static boolean AnsCheck;
	static List<District> Districts;
	
	static class District{
		//한구역 합산
		int sum=0;
		List<Country> countries = new ArrayList();
		District(){
			
		}
	}
	
	
	static class Country{
		//상하좌우
		int[] sideCheck = new int[4];
		int x;
		int y;
		Country(int x,int y){
			this.x=x;
			this.y=y;
		}
		
	}
	
	static void BFS() {
		//한구역
		Districts.add(new District());

		while(!q.isEmpty()) {
			int currentX = q.peek().x;
			int currentY = q.peek().y;
			//각 구역에 다음 나라를 집어넣는다. DistrictCnt는 현재 구역개수-1이므로 구역의 인덱스를 나타낸다
			Districts.get(DistrictCnt).countries.add(new Country(currentX,currentY));
			Districts.get(DistrictCnt).sum+=field[currentX][currentY];
			
			q.poll();
			for (int k = 0; k < 4; k++) {
				int nextX = currentX + dx[k];
				int nextY = currentY + dy[k];
				if (0 <= nextX && nextX < N && 0 <= nextY && nextY < N) {
					// 조건에 맞으면
					if (L <= Math.abs(field[nextX][nextY] - field[currentX][currentY])
							&& Math.abs(field[nextX][nextY] - field[currentX][currentY]) <= R&& !check[nextX][nextY]) {
						//한 구역당 몇 나라로 이루어져있는지 설정
						AnsCheck=true;
						q.offer(new Country(nextX,nextY));
						check[nextX][nextY]=true;
						
					}
				}

			}
		}
		//나누기 계산에서 담을 수
		int newValue = Districts.get(DistrictCnt).sum/Districts.get(DistrictCnt).countries.size();
		for(int i=0;i<Districts.get(DistrictCnt).countries.size();i++) {
			//새값 배분
			field[Districts.get(DistrictCnt).countries.get(i).x][Districts.get(DistrictCnt).countries.get(i).y] = newValue;
		}
	}
	

	//상하좌우
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		// 인구차가 L명이상, R명이하면 오픈
		// 모두 열리면 인구이동시작
		// 인접한 칸만 이용해 이동가능하면, 연합
		// 연합을 이룬 칸의 인구수는 연합인구/연합을 이루는 칸개수 (소수점버림)
		// 연합 해체후 국경선 닫기
		StringTokenizer st = new StringTokenizer(bf.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		List<Country> countries = new ArrayList<>();
		field = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(bf.readLine());
			for(int j=0; j<N;j++) {
				field[i][j] = Integer.parseInt(st.nextToken());
				
			}
		}
		int idx=0;
		while(true) {
			//배열범위 다돌면 다시 초기화
			check = new boolean[N][N];
			Districts = new ArrayList();
			DistrictCnt=0;
			AnsCheck=false;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (check[i][j] == false) {
						check[i][j]=true;
						q.offer(new Country(i, j));
						BFS();
						DistrictCnt++;
					}
				}
			}
			idx++;
			//구역 안만들어지면 반복종료
			if(AnsCheck==false) {
				break;
			}
		}
		//안만들어져도 ++ 하므로 -1 출력;
		System.out.println(idx-1);
	}
}
