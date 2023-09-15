/*
 * 국경선을 공유하는 나라들은 서로 연결되어 있기 때문에 bfs를 돌리면 찾을 수 있음.
 * 그래서 2차원 배열을 돌면서 아직 방문하지 않은 좌표에서 bfs를 돌림.
 * bfs를 돌려서 서로 연결되어 있는 나라를 구할 때는 방문체크를 해주고 리스트에 좌표값을 넣어줌.
 * 방문체크를 하기 때문에 방문하지 않은 좌표만 방문을 하게 됨.
 * 
 * 또한, 리스트의 size가 1보다 크다면 인구 이동이 발생하게 됨.
 * 리스트의 size가 모두 1이라면 더이상 인구 이동이 발생하지 않는 것임.
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int n,l,r,arr[][],time;
    static boolean visited[][];
    
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
 
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        
        //초기화
        arr = new int[n][n];
        for (int i = 0; i < n; i++) {
        	st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
				
			}
		}
        
        boolean isChange = false; //더이상 인구이동이 있는지 없는지 체크하기 위한 변수 
        while(true) {
        	isChange = false; //아직 인구이동이 있는지 없는지 모르니깐 일단 false로 초기화
        	visited = new boolean[n][n];
        	
        	//아직 방문하지 않은 좌표를 찾아야 함 
        	//방문하지 않은 좌표를 찾았다면 그 좌표에서 bfs를 돌림
        	for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					//연결되어 있는 좌표들의 리스트를 저장
					List<int[]> list = new ArrayList<>(); //현재 좌표와 연결된 모든 좌표를 저장할 리스트
					Queue<int[]> q = new ArrayDeque<>(); //bfs를 돌리기 위한 큐
					int sum=0; //리스트에 저장된 인구 수를 모두 더해줌 
					
					//아직 bfs를 돌리지 않은 좌표를 찾음
					if(visited[i][j]) continue;
					
					//시작할 좌표를 찾았으면 방문 표시하고 큐에 넣어줌
					sum+=arr[i][j];
					visited[i][j] = true;
					list.add(new int[] {i,j});
					q.add(new int[] {i,j});
					
					//이번 좌표에서 연결되어 있는 모든 좌표를 찾음 
					while(!q.isEmpty()) {
						int[] cur = q.poll();
						int curX = cur[0];
						int curY = cur[1];
						
						for(int dir=0; dir<4; dir++) {
							int nx = curX + dx[dir];
							int ny = curY + dy[dir];
							
							//좌표를 벗어났거나 이미 방문했다면 건너뜀 
							if(nx<0||nx>=n||ny<0||ny>=n) continue;
							if(visited[nx][ny]) continue;
							
							//추가로 원래 좌표와 새로 뽑은 좌표의 인구 수 차이를 구함 
							int diff = Math.abs(arr[curX][curY]-arr[nx][ny]);
							//그 차이가  l보다 작거나 r보다 크면 건너뜀
							if(diff<l || diff>r) continue;
							
							//구경을 열 수 있는 좌표를 찾았다면 방문 체크하고 리스트와 큐에 넣음 
							sum+=arr[nx][ny];
							visited[nx][ny]=true;
							list.add(new int[] {nx,ny});
							q.add(new int[] {nx,ny});
						}
					}
					
					//연결된 좌표가 2개 이상이라면 서로 국경이 열린 것이고 인구 수를 업데이트함
					if(list.size()>=2) {
						isChange = true; //이번 텀에서 국경이 열리고 인구수 업데이트된 곳이 있음
						
						//(연합의 인구수) / (연합을 이루고 있는 칸의 개수) 를 해줌
						int newNum = sum/list.size();
						for(int[] cur : list) {
							arr[cur[0]][cur[1]] = newNum;
						}
					}
				}
			}
        	
        	//더이상 이동할 수 없다면 멈춤 
        	if(!isChange) break;
        	
        	time++;
        }
        sb.append(time);
        System.out.println(sb);
    }
}