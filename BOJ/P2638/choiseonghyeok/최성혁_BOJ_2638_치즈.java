package Study;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 	치즈가 있는 부분만 사방탐색해서 2칸이상 내부공기를 만나면 삭제 처리
	시간단축을 위해 리스트에 치즈인 부분만 따로 저장할것
	단, 삭제를 바로하면 다음 치즈검사에 영향을 끼치므로 삭제할 치즈를 다른 리스트에 넣어놓고 한꺼번에 삭제시켜야함
	
	내부 공기와 외부공기는 치즈를 삭제할때마다 상태가 달라지므로 검사를 반복적으로 실행
 */

public class 최성혁_BOJ_2638_치즈 {

	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	
	static Queue<pair> q = new LinkedList();
	static int N;
	static int M;
	static int[][] field;
	static boolean[][] visited;
	
	//치즈가 담긴 리스트
	static List<pair> cheeseList;
	//지워야될 치즈좌표만 담는 리스트
	static List<pair> deleteList;
	
	//좌표 클래스
	static class pair{
		int x;
		int y;
		public pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	
	//외부 공기가 유입되고있는지 안되고있는지 처음에 모두 알아놔야한다.
	//1로 둘러 쌓여있는0은 처리를 못하게 하면된다.
	static void BFS() {
		visited = new boolean[N][M];
		q.offer(new pair(0,0));
		while(!q.isEmpty()) {
			int currentX = q.peek().x;
			int currentY = q.peek().y;
			q.poll();
			for(int i=0;i<4;i++) {
				int nextX = currentX+dx[i];
				int nextY = currentY+dy[i];
				//경계 안넘고 해당칸 방문안했으면
				if(0<=nextX&&nextX<N&&0<=nextY&&nextY<M&&visited[nextX][nextY]==false&&(field[nextX][nextY]==3||field[nextX][nextY]==0)){
					visited[nextX][nextY]=true;
					field[nextX][nextY]=3;
					q.offer(new pair(nextX,nextY));
				}
			}
		}
	}
	
	//사방탐색해서 리스트에서 해당치즈 뺌
	static void Find_N_Delete() {
		ctPoint:
		for(int i=0;i<cheeseList.size();i++) {
			//해당좌표의 주변 4칸중에 0인칸 2칸 발견하면 체크해놓는다
			int count = 0;
			for(int j=0;j<4;j++) {
				int nextX = cheeseList.get(i).x+dx[j];
				int nextY = cheeseList.get(i).y+dy[j];
				//경계 안넘고 해당칸이 0이면
				if(0<=nextX&&nextX<N&&0<=nextY&&nextY<M&&field[nextX][nextY]==3) {
					count++;
					if(count==2) {
						//삭제 리스트에 담고(삭제를 바로 하면 이후 치즈들에게 영향을 끼치므로
						deleteList.add(new pair(cheeseList.get(i).x,cheeseList.get(i).y));
						//치즈 리스트에서 해당 치즈는 없어졌음
						cheeseList.remove(i);
						i--;
						continue ctPoint;
					}
				}
			}
		}
	}
	
	
	//실제로 치즈 삭제
	static void Real_Delete() {
		for(int i=0;i<deleteList.size();i++) {
			field[deleteList.get(i).x][deleteList.get(i).y]=3;
			deleteList.remove(i);
			i--;
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st =  new StringTokenizer(bf.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		field = new int[N][M];	
		visited = new boolean[N][M];
		cheeseList = new ArrayList<>();
		deleteList = new ArrayList<>();
		for(int i=0;i<N;i++) {
			st =  new StringTokenizer(bf.readLine());
			for(int j=0;j<M;j++) {
				field[i][j] = Integer.parseInt(st.nextToken());
				if(field[i][j]==1) {
					//치즈가 있는 좌표를 리스트에 넣는다.
					cheeseList.add(new pair(i,j));
				}
			}
		}
		
		int cnt = 0;
		while(!cheeseList.isEmpty()) {
			cnt++;
			//시작하기전에 공기 유입여부 확인
			BFS();
			//치즈 삭제여부 검사
			Find_N_Delete();
			//실제 삭제행동
			Real_Delete();
		}
		System.out.println(cnt);
		
	}
}
