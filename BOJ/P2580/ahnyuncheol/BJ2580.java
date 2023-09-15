import java.io.*;
import java.util.*;

public class BJ2580 {
	
	static int[][] board;
	
	public static void main(String[] args)  throws IOException {
		
		// 입력 세팅
		System.setIn(new FileInputStream("in"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// board 구성
		board = new int[9][9];
		for (int r = 0; r < 9; r++) {
			st = new StringTokenizer(br.readLine().trim());
			for (int c = 0; c < 9; c++) {
				board[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		// blankArray 구성	: 스도쿠 빈칸의 좌표 리스트
		int cnt = 0; // 0의 개수(빈칸 개수)
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (board[r][c] == 0) { 
					cnt += 1;
				}
			}
		}
		int[][] blankArray = new int[cnt][2];
		cnt = 0;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (board[r][c] == 0) { 
					blankArray[cnt++] = new int[] {r, c};
				}
			}
		}
		
		recursive(blankArray, 0, cnt);
		print();
	}
	
	
	private static void recursive(int[][] blankArray, int idx, int cnt) {
		
		if (idx == cnt) {
			return;
		}
		
		int[] point = blankArray[idx];
		int r = point[0];
		int c = point[1];
		
		/*
		 * 빈칸에 1-9 순서대로 넣어보고 들어갈 수 있는 지 확인
		 * 들어갈 수 있으면, 다음 빈칸을 확인하는 recursive 호출
		 * 
		 * 1-9 모든 숫자를 확인한 상황
		 * -> 스도쿠를 모두 채웠거나 앞에 채운 빈칸과 충돌이 발생한 상태
		 * 따라서, 스도쿠를 모두 채웠는지 확인하고 채웠으면 return
		 * 		그렇지 않으면, 확인한 좌표(board[r][c])를 0으로 바꾸고 탈출
		 */
		Loop:
		for (int number = 1; number <= 9; number++) {
			
			// 가로 체크
			for (int i = 0; i < 9; i++) {
				if (board[r][i] == number) {
					continue Loop;
				}
			}
			
			// 세로 체크
			for (int i = 0; i < 9; i++) {
				if (board[i][c] == number) {
					continue Loop;
				}
			}
			
			// 미니 삼각형 체크
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (board[(r/3)*3 + i][(c/3)*3 + j] == number) {
						continue Loop;
					}
				}
			}
			
			board[r][c] = number;
			recursive(blankArray, idx + 1, cnt);
			
			if (chkfinish()) return;	// 스도쿠가 완성됐는지 확인하는 메소드
		}
		
		board[r][c] = 0;
	}
	
	// 스도쿠가 완성됐는지 확인하는 메소드
	private static boolean chkfinish() {
		
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (board[r][c] == 0) return false;
			}
		}
		
		return true;
	}
	
	private static void print() {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				System.out.print(board[r][c] + " ");
			}
			System.out.println();
		}
	}
}
