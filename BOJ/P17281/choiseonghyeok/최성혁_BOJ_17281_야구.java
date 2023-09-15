package Study;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
public class 최성혁_BOJ_17281_야구 {
	//1번~9번의 어떤 공격을 성공했는지 결과가 주어지고
	//1번을 4번째 순서로 고정한채 나머지 결과를 조합으로 정하여 최대값을 구하면 된다.
	//한 이닝이 3아웃이 안될경우 3아웃이 될때까지 해당 결과를 순열 순대로 진행
	//다음 이닝 넘어갔을때 마지막 순서 유지
	static int N;
	static int[][] arr;
	static int[] sel = new int[9];
	static boolean[] v = new boolean[9]; 
	static boolean[] base = new boolean[3];
	static int[] cycle = new int[9];
	static int max = Integer.MIN_VALUE;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st =  new StringTokenizer(bf.readLine());
		N = Integer.parseInt(st.nextToken());
		arr = new int[N][9];
		for(int i=0;i<N;i++) {
			st =  new StringTokenizer(bf.readLine());
			for(int j=0;j<9;j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i=0;i<9;i++) {
			cycle[i]=i;
		}
		
		//먼저 들어온 경우 순열돌리기
		v[0]=true;
		recursive(0,cycle);
		System.out.println(max);	
	}
	//사이클이 진짜 순서
	//순열이 정해졌을때의 순서 그대로 진행하는것
	//각 루에 선수가 있는지를 배열로 나타낸다
	static void recursive(int idx,int[] temp) {
		//System.out.println(sel.length);
		
		
		if(idx==sel.length) {
			//System.out.println(Arrays.toString(sel));
			//순열 만들어진걸로 계산진행
			inning(sel);
			return;
		}
		if(idx==3) {
			sel[idx]=temp[0];
			recursive(idx+1,temp);
			return;
		}
		for(int i=0;i<temp.length;i++) {
			if (v[i] == false) {
				v[i] = true;
				sel[idx]=temp[i];
				recursive(idx + 1, temp);
				v[i] = false;
			}
		}
	}
	//1루타,2루타,3루타일때 각 베이스에 주자 존재 유무에따라 점수 계산 진행해야됨
	static void inning(int[] cycles) {
		int idx=0;
		int arrindex=0;
		int sum=0;
		int outcnt=0;
		while(true) {
			//System.out.println(idx);
			//3아웃되면 두번째 배열로 초기화
			if(arr[arrindex][cycles[idx]]!=0) {
				sum = count(arr[arrindex][cycles[idx]],sum);
				//마지막번째 타순이면
				if(idx==8) {
					idx=0;
				}
				//아니면 다음타석
				else {
					idx++;
				}
			}
			else {
				outcnt++;
				//3아웃
				if(outcnt==3) {
					//베이스 초기화
					Arrays.fill(base, false);
					outcnt=0;
					// 해당 계산이 마지막 이닝이라면
					if(arrindex==N-1) {
						break;
					}
					//아니면 다음 이닝으로 이동
					else {
						arrindex++;
						if(idx==8) {
							idx=0;
						}
						//아니면 다음타석
						else {
							idx++;
						}
						continue;
					}
				}
				//아직 3아웃 전이라면
				else {
					//다음타석
					if(idx==8) {
						idx=0;
					}
					//아니면 다음타석
					else {
						idx++;
					}
				}
			}
		}
		//계산 끝났으니 최대값 계산
		max = Math.max(max, sum);
	}
	
	static int count(int hit,int sum) {
		//각각 1,2,3루타, 홈런일때
		if(hit==1) {
			if(base[2]==true) {
				sum++;
				base[2]=false;
			}
			if(base[1]==true) {
				base[2]=true;
				base[1]=false;
			}
			if(base[0]==true) {
				base[1]=true;
				base[0]=false;
			}
			base[0]=true;
		}
		else if(hit==2) {
			if(base[2]==true) {
				sum++;
				base[2]=false;
			}
			if(base[1]==true) {
				sum++;
				base[1]=false;
			}
			if(base[0]==true) {
				base[2]=true;
				base[0]=false;
			}
			base[1]=true;
		}
		else if(hit==3) {
			if(base[2]==true) {
				sum++;
				base[2]=false;
			}
			if(base[1]==true) {
				sum++;
				base[1]=false;
			}
			if(base[0]==true) {
				sum++;
				base[0]=false;
			}
			base[2]=true;
		}
		else if(hit==4) {
			if(base[2]==true) {
				sum++;
				base[2]=false;
			}
			if(base[1]==true) {
				sum++;
				base[1]=false;
			}
			if(base[0]==true) {
				sum++;
				base[0]=false;
			}
			sum++;
		}
		return sum;
	}

}
