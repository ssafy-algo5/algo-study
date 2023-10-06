import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class swea2382 {
	static class Point{
		int r, c, num, dir;

		public Point(int r, int c, int num, int dir) {
			super();
			this.r = r;
			this.c = c;
			this.num = num;
			this.dir = dir;
		}

		public void move() {
			if(dir==1) {
				r--;
			}
			else if(dir==2) {
				r++;
			}
			else if(dir==3) {
				c--;
			}
			else if(dir==4) {
				c++;
			}
			
			//한칸 움직이고 나서 테투리에 위치하는지 검사해줌
			if(r==0 || r==n-1 || c==0 || c==n-1) {
				//방향 바꿔줌 
				if(dir==1) dir=2;
				else if(dir==2) dir=1;
				else if(dir==3) dir=4;
				else if(dir==4) dir=3;
				
				//미생물 개수 반으로 줄어듦
				num /= 2;
			}
		}
		
	}
	
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	static int t, n, m, k;
	static List<Point>[][] map, newMap;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		t = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= t; tc++) {
			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken()); //한 변 셀의 개수
			m = Integer.parseInt(st.nextToken()); //격리 시간
			k = Integer.parseInt(st.nextToken()); //군집의 개수 
			
			//초기화
			map = new ArrayList[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					map[i][j] = new ArrayList<>();
				}
			}
			
			//k번 군집을 입력받음
			while(k-- > 0) {
				st = new StringTokenizer(br.readLine());
				int r = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				int num = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken());
				
				//초반 상태를 맵에 저장해줌 
				//초반 상태는 같은 셀에 배치되어 있는 군집이 없으므로 그냥 넣어주기만 하면 됨 
				map[r][c].add(new Point(r,c,num,dir)); 
			}
			
			//m시간동안 군집을 옮겨줌 
			move();
			
			//답 구하기
			sb = new StringBuilder();
			sb.append("#"+tc+" "+answer());
            System.out.println(sb);
		}
	}

	private static int answer() {
		int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < map[i][j].size(); k++) {
                    ans += map[i][j].get(k).num;
                }
            }
        }
        
        return ans;
	}

	private static void move() {
		//m번 군집을 옮겨주면서 map에서 상태를 저장해줌
		while(m-- > 0) {
			//매 시간마다 새로운 좌표에 군집이 생성되므로 이번 m시간에 만들어지는
			//군집 정보를 저장할 새로운 맵
			newMap = new ArrayList[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					newMap[i][j] = new ArrayList<>();
				}
			}
			
			//list에 값이 있는 경우에는 꺼내서 옮겨줌 
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					for (int k = 0; k < map[i][j].size(); k++) {
						//해당 셀을 이동을 시켜주고 테투리에 가게 되면 방향과 미생물 수를 변경해줌
						map[i][j].get(k).move();
						
						int nr = map[i][j].get(k).r;
						int nc = map[i][j].get(k).c;

						//움직였는데 더이상 미생물이 남아있지 않다면 newMap에 추가해주지 않아도 됨
						//미생물이 남아있다면 새롭게 움직인 좌표의 list에 현재 point를 저장해주면 됨 
						if(map[i][j].get(k).num!=0) {
							newMap[nr][nc].add(map[i][j].get(k));
						}
						
						//원래 맵에서는 삭제해야함
//						map[i][j].remove(k);
//						k--;
						
					}
				}
			}
			//새로 옮겨진 맵으로 갱신해줌 
			map = newMap;
			
			//다 옮기고 나서 list의 size가 2이상인 좌표가 있다면 거기 point 애들을 이용해서
			//새로운 point를 만들고 원래 있는 point는 다 삭제해줌 
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if(map[i][j].size()>1) {
						int dir=-1, sum=0, max=0;
						
						//2개 이상의 point들을 모두 보고
						for (int k = 0; k < map[i][j].size(); k++) {
							Point cur = map[i][j].get(k);
							sum += cur.num;
							if(cur.num > max) {
								max = cur.num;
								dir = cur.dir;
							}
						}
						
						//현재 있는 point를 모두 삭제함 
						map[i][j].clear();
						
						//하나의 point로 합쳐줌 
						map[i][j].add(new Point(i,j,sum,dir));
					}
				}
			}
		}
	}
}
