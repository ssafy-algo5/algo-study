package Study;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
 * 백트래킹으로 접근
 * 입력받을때 0이였던 자리를 기억해놓고, 해당 좌표의 스도쿠의 기본법칙인 가로 세로, 그리고 해당 좌표가 속한 3x3칸을 검사후 재귀진행 
 */

public class 최성혁_BOJ_2580_스도쿠{
	static List<pair> blanks = new ArrayList();
	static int[][] sudoku;
	static class pair{
		int x;
		int y;
		public pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		sudoku = new int[9][9];
		for(int i=0;i<9;i++) {
			StringTokenizer st = new StringTokenizer(bf.readLine());
			for (int j = 0; j < 9; j++) {
				sudoku[i][j] = Integer.parseInt(st.nextToken());
				if(sudoku[i][j]==0) {
					blanks.add(new pair(i,j));
				}
			}
		}
		//처음수에 모두 다 넣어놓으며 재귀 시작
		for(int i=1;i<=9;i++) {
			sudoku[blanks.get(0).x][blanks.get(0).y] = i;
			check(0);
			sudoku[blanks.get(0).x][blanks.get(0).y] = 0;
		}
	}
	//정답이 여러개면 하나만 출력하게 하기 위한 boolean변수
	static boolean ansCheck=false;
	public static void check(int idx){
		int x = blanks.get(idx).x;
		int y = blanks.get(idx).y;
		//만약 스도쿠 조건에 안맞는다면 
		for(int i=0;i<9;i++){
            //자신제외
            //세로
            if(x!=i){
            	//세로에 자기랑 같은수가 있다면
                if(sudoku[i][y]==sudoku[x][y]){
                    return;
                	}
            	}
            //가로
            if(y!=i){
            	//가로에 자기랑 같은수가 있다면
                if(sudoku[x][i]==sudoku[x][y]){
                	return;
                }
            }
        }
		int startX = x/3 * 3;
		int startY = y/3 * 3;
		for(int i = startX;i<startX+3;i++) {
			for(int j=startY;j<startY+3;j++){
				if(x!=i&&y!=j) { //자기제외
					//자기랑 같은수가 3x3칸 안에 있다면
					if(sudoku[x][y]==sudoku[i][j]) {
						return;
					}
				}
			}
		} 
		//끝까지 닿았으므로 이게 정답
		if(idx==blanks.size()-1) {
			if(ansCheck==false) { //정답이 여러개면 하나만 출력
				ansCheck=true;
				print(sudoku);
			}
			return;
		}
		
		//조건 만족하므로 다음단계로
		for(int i=1;i<=9;i++) {
			//처음과 마찬가지로 모든 수 다 넣어봄
			sudoku[blanks.get(idx+1).x][blanks.get(idx+1).y] = i;
			check(idx+1);
			sudoku[blanks.get(idx+1).x][blanks.get(idx+1).y] = 0;
		}
	}
	
	static void print(int[][] arr) {
		for(int i=0;i<9;i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(sudoku[i][j]+" ");
			}
			System.out.println();
		}
	}
}
