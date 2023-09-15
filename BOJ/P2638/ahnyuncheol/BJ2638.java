package Baekjoon;

import java.io.*;
import java.util.*;

public class BJ2638 {
	
	static int[][] board;
	static int R;
	static int C;
	static int[] dr = {0, 0, 1, -1};
	static int[] dc = {1, -1, 0, 0};
	
	public static void main(String[] args) throws IOException {
		/*
		 * 치즈 : 1
		 * 치즈 외부 접촉면 : -1
		 * 치즈 내부 빈 공간 : 0
		 */
		
		// 입력 세팅
		System.setIn(new FileInputStream("in"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// board(모눈종이) 변수 생성
		st = new StringTokenizer(br.readLine().trim());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		board = new int[R][C];
		
		// board(모눈종이) 구성)
		for (int r = 0; r < R; r++) {
			st = new StringTokenizer(br.readLine().trim());
			for (int c = 0; c < C; c++) {
				board[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		makeArea(0, 0);	// 치즈와 접촉 가능한 면을 -1로 만드는 함수
		System.out.println(cheese());
	}
	
	private static int cheese() {
		
		int answer = 0;	// 치즈가 녹는 데 걸리는 시간
		List<int[]> list = new LinkedList<>();	// 순간에 녹는 치즈 좌표를 담는 배열
		
		while (true) {	// 치즈가 없을 때 까지 반복
			if (noCheese(board)) break;	// 치즈가 없으면 탈출
			answer += 1;
			
			int cnt, cr, cc;	// 치즈 외부 접촉면 count, current row, current column
			// list에 담기
			for (int r = 1; r < R-1; r++) {	// 모눈종이의 가장자리는 치즈가 놓이지 않음
				for (int c = 1; c < C-1; c++) {	// 모눈종이의 가장자리는 치즈가 놓이지 않음
					
					if (board[r][c] == 1) {
						cnt = 0;	// 치즈 외부 접촉면 count
						
						for (int i = 0; i < 4; i++) {
							cr = r + dr[i];	// current row
							cc = c + dc[i];	// current column
							
							if (board[cr][cc] == -1) cnt += 1;
						}
						
						if (cnt >= 2) list.add(new int[] {r, c});
					}
				}
			}
			
			// list의 치즈 제거
			for (int[] position: list) {	// position : [row, column]
				board[position[0]][position[1]] = -1;
				
				for (int i = 0; i < 4; i++) {
					cr = position[0] + dr[i];	// current row
					cc = position[1] + dc[i];	// current column
					
					// 치즈를 제거하고 사방탐색을 했을 때, 0이 위치하면 그곳으로부터 이어진 0들을 -1로 변환
					if (board[cr][cc] == 0) {
						makeArea(cr, cc);
					}
				}
			}
			
			list.clear();
		}
		
		return answer;
	}
	
	// 모눈종이에 치즈가 남아있는지 체크
	private static boolean noCheese(int[][] board) {
		
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] != -1) return false;
			}
		}
		
		return true;
	}
	
	// row: x, column: y인 좌표로부터 이어진 0들을 -1로 변환하는 메소드
	private static void makeArea(int x, int y) {
		
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {x, y});
		board[x][y] = -1;
		
		int[] element = new int[2];
		int r, c, cr, cc;
		
		while (!q.isEmpty()) {
			element = q.poll();
			r = element[0];
			c = element[1];
			
			for (int i = 0; i < 4; i++) {
				cr = r + dr[i];
				cc = c + dc[i];
				
				if (cr >= 0 && cr < R && cc >= 0 && cc < C) {
					if (board[cr][cc] == 0) {
						q.add(new int[] {cr, cc});
						board[cr][cc] = -1;
					}
				}
			}
		}
	}
	
	// 테스트용 프린트 함수
	private static void print(int[][] board) {
		int R = board.length;
		int C = board[0].length;
		
		for (int r = 0; r < R; r++) {
			for (int c = 0; c < C; c++) {
				System.out.print(board[r][c] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}
}
