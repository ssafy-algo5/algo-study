package Study.day0921;

/*
 * 두 사람이 직접적으로 아는 관계뿐만 아니라 몇 사람을 거쳐서 알 수 있는 경우도
 * 모두 하나의 무리라고 볼 수 있기 때문에 한 사람으로부터 탐색을 시작하는 BFS로 풀어야겠다고 생각함.
 * 
 * n이 100으로 많이 크지 않지만 100*100은 10000이므로 이차원배열 대신 list를 사용하여 서로의 관계를 저장해줌.
 * 각각의 사람은 본인의 지인을 arrayList로 연결하여 저장할 것이고, 이러한 list가 n개만큼 있는 상황임.
 * 
 * 모든 사람이 확인됐는지를 위한 check배열이 있음.
 * check배열에서 모든 사람이 체크됐다면 탐색은 멈춤.
 * 한 명이라도 남아있다면 check가 되지 않은 한 사람을 뽑아 BFS를 돌림.
 * 그 사람의 지인을 모두 큐에 넣고 확인하는 것. 
 * 이 때 check되지 않은 사람만 큐에 넣을 것!
 * 
 * 그렇게 모든 사람을 모두 보았다면 끝임 
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA7465 {
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	static int t, n, m, ans;
	static List<Integer>[] list; //n명이 자신의 친구를 저장함 
	static boolean[] check; //본인이 확인 됐는지 안됐는지 체크하는 배열 
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		
		t = Integer.parseInt(br.readLine()); //t 입력받기
		
		for(int tc=1; tc<=t; tc++) {
			input(); //입력 받기 및 변수 초기화 

			ans = solve(); //bfs를 돌면서 그룹의 개수 구하기 
			
			sb.append("#"+tc+" "+ans);
			System.out.println(sb);
		}
	}

	private static void input() throws IOException {
		sb = new StringBuilder();
		st = new StringTokenizer(br.readLine()); 
		n = Integer.parseInt(st.nextToken()); //n 입력받기
		m = Integer.parseInt(st.nextToken()); //m 입력받기
		
		//초기화
		ans = 0;
		check = new boolean[n]; //n명을 체크하기 위한 배열 
		list = new ArrayList[n]; //ArrayList가 n개 있는 배열임
		for (int i = 0; i < n; i++) { //각 배열의 객체는 ArrayList이고 이 리스트를 만들어줘야 함
			list[i] = new ArrayList<>();
		}
		
		while(m-- > 0) { //m번 만큼 돌면서 친구 목록을 만들어줌 
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			
			list[a].add(b); //a친구는 b
			list[b].add(a); //****이거 안해주면 안됨 
//			ex)
//			1
//			7 3
//			1 3
//			3 4
		}
		
//		for (List x : list) {
//			for (Object a : x) {
//				System.out.print(a+" ");
//			}
//			System.out.println();
//		}
		
	}
	
	private static int solve() {
		int ans = 0; //그룹이 총 몇 팀인지 저장
		Queue<Integer> q = new LinkedList<>();
		
		while(true) { //모두가 확인되어야 함 
			int idx = find(); //find배열에 시작점으로 찾을 친구를 구해옴 
			
			if(idx==-1) break; //모든 사람을 다 보았으므로 while문 탈출
			
			ans++; //새로운 무리를 찾았음 
			check[idx]=true; //확인했으니 체크 표시해줌 
			q.add(idx); //큐에 넣어줌 
			
			while(!q.isEmpty()) { //연결되어있는 친구를 모두 봐야 함
				int cur = q.poll();
				
				for (Integer x : list[cur]) { //cur의 친구들인 x를 모두 큐에 넣음 
					if(!check[x]) { //아직 방문하지 않은 친구들만 넣어줌
						check[x]=true; //방문 체크 후
						q.add(x); //큐에 넣어줌 
					}
				}
			}
		}
		
		
		return ans;
	}

	private static int find() {
		// check배열을 돌면서 false인 즉, 아직 확인되지 않은 친구를 넘겨줌 
		for (int i = 0; i < n; i++) {
			if(!check[i]) return i;
		}
		return -1; //모두가 체크되었다면 -1을 리턴해줌 
	}
}
