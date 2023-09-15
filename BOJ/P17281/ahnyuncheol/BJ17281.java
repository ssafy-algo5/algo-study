package Baekjoon;

import java.io.*;
import java.util.*;

public class BJ17281 {
	static int N;
	static int[][] ordered;
	static int max = Integer.MIN_VALUE;
	static int cnt = 0;

	public static void main(String[] args) throws IOException {
		
		// 입력 세팅
		System.setIn(new FileInputStream("in"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// ordered[][](선수별 이닝별 결과) 구성
		N = Integer.parseInt(br.readLine());
		ordered = new int[N][9];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 9; j++) {
				ordered[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		boolean[] chk = new boolean[9];	// 순열을 만들기 위한 check 배열
		chk[0] = true;
		int[] sel = new int[9];			// 타자 순서 배열
		sel[3] = 0;
		
		
		recursive(sel, chk, 0);
		System.out.println(max);
		//System.out.println(cnt);
	}
	
	
	private static void recursive(int[] sel, boolean[] chk, int idx) {
		
		if (idx == 3) {
			recursive(sel, chk, idx+1);
			return;
		}
		
		if (idx == 9) {
			//cnt++;
			countscore(sel);	// sel : 타순 배열 sel[0]번 부터 ~ sel[8]번의 순서로
			return;
		}
		
		for (int i = 1; i < 9; i++) {
			if (chk[i] == false) {
				chk[i] = true;
				sel[idx] = i;
				recursive(sel, chk, idx+1);
				chk[i] = false;
			}
		}
	}
	
	private static void countscore(int[] sel) {
		
		int innings = 0;	// 이닝
		int outCount = 0;	// 아웃 카운트
		boolean[] base = new boolean[4];	// 베이스(출발, 1루, 2루, 3루)
		int hit;			// 0(아웃) 1 2 3 4
		int score = 0;		// 스코어
		
		Loop:
		while (true) {
			
			// sel(타자 순서 배열) 반복
			for (int i: sel) {
				
				hit = ordered[innings][i];	// 0(아웃) 1 2 3 4
				if (hit == 0) {				// 아웃
					if (++outCount == 3) {	// 다음 이닝 혹은 종료!
						if (++innings < N){	// 다음 이닝
							outCount = 0;
							// base 초기화
							for (int k = 0; k < 4; k++) {
								base[k] = false;
							}
							continue;		// 반복문 : for (int i: sel)으로 이동!
						} else {			// 종료
							break Loop;
						}
					}
				}
				
				// if 0 < hit <= 4
				base[0] = true;
				for (int k = 3; k >= 0; k--) {
					if (base[k] == true) {
						
						// 1
						base[k] = false;
						// 2
						if (k + hit >= 4) score += 1;
						else base[k + hit] = true;
					
						// why? 1과 2 순서를 바꿨을 때 값이 다르지...?
					}
//					else if base[k] == false
//					: 변화 없음
				}
			}
		}
		
		if (score > max) {
			//System.out.println(Arrays.toString(sel) + " " + score);
			max = score;
		}
	}
}
